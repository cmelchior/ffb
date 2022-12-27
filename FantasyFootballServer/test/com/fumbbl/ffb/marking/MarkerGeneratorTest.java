package com.fumbbl.ffb.marking;

import com.fumbbl.ffb.factory.SkillFactory;
import com.fumbbl.ffb.model.Player;
import com.fumbbl.ffb.model.Position;
import com.fumbbl.ffb.model.skill.Skill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class MarkerGeneratorTest {

	private static final String BLOCK_MARKING = "B";
	private static final String BLODGE_MARKING = "X";
	private static final String DODGE_MARKING = "D";
	private static final String BLACKLE_MARKING = "Y";
	private static final String WRECKLE_MARKING = "Q";

	private static final String TACKLE_MARKING = "T";
	private static final String WRESTLE_MARKING = "W";
	private static final String SNEAKY_GIT = "sneaky git";
	private static final String BLOCK = "block";
	private static final String DODGE = "dodge";
	private static final String TACKLE = "tackle";
	private static final String WRESTLE = "wrestle";

	private final MarkerGenerator generator = new MarkerGenerator();
	private AutoMarkingConfig config;
	private AutoMarkingRecord.Builder builder;
	private SkillFactory skillFactory;
	private Set<AutoMarkingRecord> markings;
	@Mock
	private Player<Position> player;
	@Mock
	private Position position;

	@BeforeEach
	public void setUp() {
		skillFactory = new MockSkillFactory();
		config = new AutoMarkingConfig();
		builder = new AutoMarkingRecord.Builder(skillFactory);
		markings = config.getMarkings();

		Skill[] gainedSkills = {skillFactory.forName(BLOCK), skillFactory.forName(DODGE)};
		given(player.getSkills()).willReturn(gainedSkills);
		Skill[] baseSkills = {skillFactory.forName(WRESTLE), skillFactory.forName(TACKLE)};
		given(player.getPosition()).willReturn(position);
		given(position.getSkills()).willReturn(baseSkills);
	}

	@Test
	public void generate() {
		config.getMarkings().add(builder.withSkill(BLOCK).withMarking(BLOCK_MARKING).build());

		String marking = generator.generate(player, config, true);

		assertEquals(BLOCK_MARKING, marking);
	}

	@Test
	public void generateNoMarking() {
		config.getMarkings().add(builder.withSkill(SNEAKY_GIT).withMarking(BLOCK_MARKING).build());

		String marking = generator.generate(player, config, true);

		assertTrue(marking.isEmpty());
	}

	@Test
	public void generateOnlyForPresentSkills() {
		markings.add(builder.withSkill(BLOCK).withMarking(BLOCK_MARKING).build());
		markings.add(builder.withSkill(SNEAKY_GIT).withMarking(DODGE_MARKING).build());

		String marking = generator.generate(player, config, true);

		assertEquals(BLOCK_MARKING, marking);
	}

	@Test
	public void generateMarkingsForOverlappingConfigs() {
		markings.add(builder.withSkill(BLOCK).withSkill(DODGE).withMarking(BLODGE_MARKING).build());
		markings.add(builder.withSkill(BLOCK).withSkill(TACKLE).withMarking(BLACKLE_MARKING).build());

		String marking = generator.generate(player, config, true);

		assertEquals(BLACKLE_MARKING + BLODGE_MARKING, marking);
	}

	@Test
	public void ignoreCombinedConfigsForGainedSkillsWithOnlyPartialMatch() {
		markings.add(builder.withSkill(BLOCK).withSkill(DODGE).withMarking(BLODGE_MARKING).build());
		markings.add(builder.withSkill(BLOCK).withSkill(TACKLE).withMarking(BLACKLE_MARKING).withGainedOnly(true).build());

		String marking = generator.generate(player, config, true);

		assertEquals(BLODGE_MARKING, marking);
	}

	@Test
	public void ignoreSubsets() {
		markings.add(builder.withSkill(BLOCK).withSkill(DODGE).withMarking(BLODGE_MARKING).build());
		markings.add(builder.withSkill(DODGE).withMarking(DODGE_MARKING).build());

		String marking = generator.generate(player, config, true);

		assertEquals(BLODGE_MARKING, marking);
	}

	@Test
	public void generateForAllMatchingConfigs() {
		markings.add(builder.withSkill(BLOCK).withMarking(BLOCK_MARKING).build());
		markings.add(builder.withSkill(DODGE).withMarking(DODGE_MARKING).build());

		String marking = generator.generate(player, config, true);

		assertEquals(BLOCK_MARKING + DODGE_MARKING, marking);
	}

	@Test
	public void generateForAllMatchingConfigsWithOpponent() {
		markings.add(builder.withSkill(BLOCK).withMarking(BLOCK_MARKING).build());
		markings.add(builder.withSkill(DODGE).withMarking(DODGE_MARKING).build());

		String marking = generator.generate(player, config, false);

		assertEquals(BLOCK_MARKING + DODGE_MARKING, marking);
	}

	@Test
	public void generateForAllMatchingConfigsWithMatchingApplyTo() {
		markings.add(builder.withSkill(BLOCK).withMarking(BLOCK_MARKING).withApplyTo(ApplyTo.OWN).build());
		markings.add(builder.withSkill(DODGE).withMarking(DODGE_MARKING).withApplyTo(ApplyTo.OPPONENT).build());

		String marking = generator.generate(player, config, true);

		assertEquals(BLOCK_MARKING, marking);
	}

	@Test
	public void generateForAllMatchingConfigsWithOpponentAndMatchingApplyTo() {
		markings.add(builder.withSkill(BLOCK).withMarking(BLOCK_MARKING).withApplyTo(ApplyTo.OWN).build());
		markings.add(builder.withSkill(DODGE).withMarking(DODGE_MARKING).withApplyTo(ApplyTo.OPPONENT).build());

		String marking = generator.generate(player, config, false);

		assertEquals(DODGE_MARKING, marking);
	}

	@Test
	public void generateForAllMatchingConfigsWithGainedOnly() {
		markings.add(builder.withSkill(WRESTLE).withMarking(WRECKLE_MARKING).withGainedOnly(true).build());
		markings.add(builder.withSkill(DODGE).withMarking(DODGE_MARKING).withGainedOnly(true).build());

		String marking = generator.generate(player, config, true);

		assertEquals(WRESTLE_MARKING, marking);
	}

	@Test
	public void generateForAllMatchingConfigsWithOpponentAndGainedOnly() {
		markings.add(builder.withSkill(WRESTLE).withMarking(WRECKLE_MARKING).withGainedOnly(true).build());
		markings.add(builder.withSkill(DODGE).withMarking(DODGE_MARKING).withGainedOnly(true).build());

		String marking = generator.generate(player, config, false);

		assertEquals(WRESTLE_MARKING, marking);
	}

	@Test
	public void generateForAllMatchingConfigsWithMatchingGainedAndApplyTo() {
		markings.add(builder.withSkill(BLOCK).withMarking(BLOCK_MARKING).withApplyTo(ApplyTo.OWN).build());
		markings.add(builder.withSkill(DODGE).withMarking(DODGE_MARKING).withGainedOnly(true).withApplyTo(ApplyTo.OWN).build());
		markings.add(builder.withSkill(WRESTLE).withMarking(WRESTLE_MARKING).withApplyTo(ApplyTo.OPPONENT).build());
		markings.add(builder.withSkill(TACKLE).withMarking(TACKLE_MARKING).withGainedOnly(true).withApplyTo(ApplyTo.OPPONENT).build());

		String marking = generator.generate(player, config, true);

		assertEquals(BLOCK_MARKING, marking);
	}

	@Test
	public void generateForAllMatchingConfigsWithOpponentAndMatchingGainedAndApplyTo() {
		markings.add(builder.withSkill(BLOCK).withMarking(BLOCK_MARKING).withApplyTo(ApplyTo.OWN).build());
		markings.add(builder.withSkill(DODGE).withMarking(DODGE_MARKING).withGainedOnly(true).withApplyTo(ApplyTo.OWN).build());
		markings.add(builder.withSkill(WRESTLE).withMarking(WRESTLE_MARKING).withApplyTo(ApplyTo.OPPONENT).build());
		markings.add(builder.withSkill(TACKLE).withMarking(TACKLE_MARKING).withGainedOnly(true).withApplyTo(ApplyTo.OPPONENT).build());

		String marking = generator.generate(player, config, false);

		assertEquals(DODGE_MARKING, marking);
	}

	private static class MockSkillFactory extends SkillFactory {

		private final Map<String, Skill> skills = new HashMap<>();

		@Override
		public Skill forName(String name) {
			name = name.toLowerCase();
			if (!skills.containsKey(name)) {
				Skill skill = mock(Skill.class);
//				given(skill.getName()).willReturn(name);
				skills.put(name, skill);
			}

			return skills.get(name);
		}
	}
}