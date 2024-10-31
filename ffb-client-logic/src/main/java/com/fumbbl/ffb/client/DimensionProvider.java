package com.fumbbl.ffb.client;

import com.fumbbl.ffb.Direction;
import com.fumbbl.ffb.FieldCoordinate;

import javax.swing.border.TitledBorder;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class DimensionProvider {

	private final LayoutSettings layoutSettings;

	public DimensionProvider(LayoutSettings layoutSettings) {
		this.layoutSettings = layoutSettings;
	}

	public LayoutSettings getLayoutSettings() {
		return layoutSettings;
	}

	public Dimension dimension(Component component) {
		return scale(unscaledDimension(component));
	}

	public Dimension dimension(Component component, double scale) {
		Dimension dimension = unscaledDimension(component);
		return new Dimension(scale(dimension.width, scale), scale(dimension.height, scale));
	}

	public Dimension unscaledDimension(Component component) {
		return component.dimension(layoutSettings.getLayout());
	}

	public boolean isPitchPortrait() {
		return layoutSettings.getLayout().isPortrait();
	}

	public FieldCoordinate mapToGlobal(FieldCoordinate fieldCoordinate) {
		if (isPitchPortrait()) {
			return new FieldCoordinate(25 - fieldCoordinate.getY(), fieldCoordinate.getX());
		}

		return fieldCoordinate;
	}

	public int fieldSquareSize() {
		return fieldSquareSize(1);
	}

	public int fieldSquareSize(double factor) {
		return (int) scale(unscaledFieldSquare() * factor);
	}

	public int unscaledFieldSquare() {
		return unscaledDimension(Component.FIELD_SQUARE).width;
	}

	public int imageOffset() {
		return fieldSquareSize() / 2;
	}

	public Dimension mapToLocal(int x, int y, boolean addImageOffset) {
		int offset = addImageOffset ? unscaledFieldSquare() / 2 : 0;


		if (isPitchPortrait()) {
			return scale(new Dimension(y * unscaledFieldSquare() + offset, (25 - x) * unscaledFieldSquare() + offset));
		}
		return scale(new Dimension(x * unscaledFieldSquare() + offset, y * unscaledFieldSquare() + offset));

	}

	public Dimension mapToLocal(FieldCoordinate fieldCoordinate) {
		return mapToLocal(fieldCoordinate, false);
	}

	public Dimension mapToLocal(FieldCoordinate fieldCoordinate, boolean addImageOffset) {
		return mapToLocal(fieldCoordinate.getX(), fieldCoordinate.getY(), addImageOffset);
	}

	public Direction mapToLocal(Direction direction) {
		if (isPitchPortrait()) {
			switch (direction) {
				case NORTHEAST:
					return Direction.NORTHWEST;
				case EAST:
					return Direction.NORTH;
				case SOUTHEAST:
					return Direction.NORTHEAST;
				case SOUTHWEST:
					return Direction.SOUTHEAST;
				case WEST:
					return Direction.SOUTH;
				case NORTHWEST:
					return Direction.SOUTHWEST;
				case NORTH:
					return Direction.WEST;
				case SOUTH:
					return Direction.EAST;
			}
		}

		return direction;
	}

	public Dimension scale(Dimension dimension) {
		return new Dimension(scale(dimension.width), scale(dimension.height));
	}

	public int scale(int size) {
		return scale(size, layoutSettings.getScale());
	}

	public int scale(int size, double scale) {
		return (int) (size * scale);
	}

	public double scale(double size) {
		return (size * layoutSettings.getScale());
	}

	public Rectangle scale(Rectangle rectangle) {
		return new Rectangle(scale(rectangle.x), scale(rectangle.y), scale(rectangle.width), scale(rectangle.height));
	}

	public BufferedImage scaleImage(BufferedImage pImage) {
		if (layoutSettings.getScale() == 1) {
			return pImage;
		}

		BufferedImage scaledImage = new BufferedImage(scale(pImage.getWidth()), scale(pImage.getHeight()), BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		at.scale(layoutSettings.getScale(), layoutSettings.getScale());
		AffineTransformOp scaleOp =
			new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

		try {
			scaledImage = scaleOp.filter(pImage, scaledImage);
		} catch (Exception e) {
			// ignore
		}
		return scaledImage;
	}

	public void scaleFont(java.awt.Component component) {
		Font font = component.getFont();
		if (font != null) {
			component.setFont(new Font(font.getFamily(), font.getStyle(), scale(font.getSize())));
		}
	}


	public TitledBorder scaleFont(TitledBorder border) {
		Font font = border.getTitleFont();
		if (font != null) {
			border.setTitleFont(new Font(font.getFamily(), font.getStyle(), scale(font.getSize())));
		}
		return border;
	}

}
