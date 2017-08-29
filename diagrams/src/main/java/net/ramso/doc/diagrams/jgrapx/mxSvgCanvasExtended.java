package net.ramso.doc.diagrams.jgrapx;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.mxgraph.canvas.mxSvgCanvas;
import com.mxgraph.shape.mxIShape;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.util.mxUtils;

import net.ramso.doc.diagrams.tools.DiagramConstants;

public class mxSvgCanvasExtended extends mxSvgCanvas {
	private static final String SPACE = " ";
	protected static Map<String, mxIShape> shapes = new HashMap<String, mxIShape>();
	// ... have coppied only the related code

	public mxSvgCanvasExtended(Document createSvgDocument) {
		super(createSvgDocument);
	}

	@Override
	public Element drawShape(int x, int y, int w, int h, Map<String, Object> style) {
		{
			String fillColor = mxUtils.getString(style, mxConstants.STYLE_FILLCOLOR, "none");
			String gradientColor = mxUtils.getString(style, mxConstants.STYLE_GRADIENTCOLOR, "none");
			String strokeColor = mxUtils.getString(style, mxConstants.STYLE_STROKECOLOR, "none");
			float strokeWidth = (float) (mxUtils.getFloat(style, mxConstants.STYLE_STROKEWIDTH, 1) * scale);
			float opacity = mxUtils.getFloat(style, mxConstants.STYLE_OPACITY, 100);
			float fillOpacity = mxUtils.getFloat(style, mxConstants.STYLE_FILL_OPACITY, 100);
			float strokeOpacity = mxUtils.getFloat(style, mxConstants.STYLE_STROKE_OPACITY, 100);

			// Draws the shape
			String shape = mxUtils.getString(style, mxConstants.STYLE_SHAPE, "");
			Element elem = null;
			Element background = null;

			if (shape.equals(mxConstants.SHAPE_IMAGE)) {
				String img = getImageForStyle(style);

				if (img != null) {
					// Vertical and horizontal image flipping
					boolean flipH = mxUtils.isTrue(style, mxConstants.STYLE_IMAGE_FLIPH, false);
					boolean flipV = mxUtils.isTrue(style, mxConstants.STYLE_IMAGE_FLIPV, false);

					elem = createImageElement(x, y, w, h, img, PRESERVE_IMAGE_ASPECT, flipH, flipV, isEmbedded());
				}
			} else if (shape.equals(mxConstants.SHAPE_LINE)) {
				String direction = mxUtils.getString(style, mxConstants.STYLE_DIRECTION, mxConstants.DIRECTION_EAST);
				String d = null;

				if (direction.equals(mxConstants.DIRECTION_EAST) || direction.equals(mxConstants.DIRECTION_WEST)) {
					int mid = (y + h / 2);
					d = "M " + x + " " + mid + " L " + (x + w) + " " + mid;
				} else {
					int mid = (x + w / 2);
					d = "M " + mid + " " + y + " L " + mid + " " + (y + h);
				}

				elem = document.createElement("path");
				elem.setAttribute("d", d + " Z");
			} else if (shape.equals(mxConstants.SHAPE_ELLIPSE)) {
				elem = document.createElement("ellipse");

				elem.setAttribute("cx", String.valueOf(x + w / 2));
				elem.setAttribute("cy", String.valueOf(y + h / 2));
				elem.setAttribute("rx", String.valueOf(w / 2));
				elem.setAttribute("ry", String.valueOf(h / 2));
			} else if (shape.equals(mxConstants.SHAPE_DOUBLE_ELLIPSE)) {
				elem = document.createElement("g");
				background = document.createElement("ellipse");
				background.setAttribute("cx", String.valueOf(x + w / 2));
				background.setAttribute("cy", String.valueOf(y + h / 2));
				background.setAttribute("rx", String.valueOf(w / 2));
				background.setAttribute("ry", String.valueOf(h / 2));
				elem.appendChild(background);

				int inset = (int) ((3 + strokeWidth) * scale);

				Element foreground = document.createElement("ellipse");
				foreground.setAttribute("fill", "none");
				foreground.setAttribute("stroke", strokeColor);
				foreground.setAttribute("stroke-width", String.valueOf(strokeWidth));

				foreground.setAttribute("cx", String.valueOf(x + w / 2));
				foreground.setAttribute("cy", String.valueOf(y + h / 2));
				foreground.setAttribute("rx", String.valueOf(w / 2 - inset));
				foreground.setAttribute("ry", String.valueOf(h / 2 - inset));
				elem.appendChild(foreground);
			} else if (shape.equals(mxConstants.SHAPE_RHOMBUS)) {
				elem = document.createElement("path");

				String d = "M " + (x + w / 2) + " " + y + " L " + (x + w) + " " + (y + h / 2) + " L " + (x + w / 2)
						+ " " + (y + h) + " L " + x + " " + (y + h / 2);

				elem.setAttribute("d", d + " Z");
			} else if (shape.equals(mxConstants.SHAPE_TRIANGLE)) {
				elem = document.createElement("path");
				String direction = mxUtils.getString(style, mxConstants.STYLE_DIRECTION, "");
				String d = null;

				if (direction.equals(mxConstants.DIRECTION_NORTH)) {
					d = "M " + x + " " + (y + h) + " L " + (x + w / 2) + " " + y + " L " + (x + w) + " " + (y + h);
				} else if (direction.equals(mxConstants.DIRECTION_SOUTH)) {
					d = "M " + x + " " + y + " L " + (x + w / 2) + " " + (y + h) + " L " + (x + w) + " " + y;
				} else if (direction.equals(mxConstants.DIRECTION_WEST)) {
					d = "M " + (x + w) + " " + y + " L " + x + " " + (y + h / 2) + " L " + (x + w) + " " + (y + h);
				} else
				// east
				{
					d = "M " + x + " " + y + " L " + (x + w) + " " + (y + h / 2) + " L " + x + " " + (y + h);
				}

				elem.setAttribute("d", d + " Z");
			} else if (shape.equals(mxConstants.SHAPE_HEXAGON)) {
				elem = document.createElement("path");
				String direction = mxUtils.getString(style, mxConstants.STYLE_DIRECTION, "");
				String d = null;

				if (direction.equals(mxConstants.DIRECTION_NORTH) || direction.equals(mxConstants.DIRECTION_SOUTH)) {
					d = "M " + (x + 0.5 * w) + " " + y + " L " + (x + w) + " " + (y + 0.25 * h) + " L " + (x + w) + " "
							+ (y + 0.75 * h) + " L " + (x + 0.5 * w) + " " + (y + h) + " L " + x + " " + (y + 0.75 * h)
							+ " L " + x + " " + (y + 0.25 * h);
				} else {
					d = "M " + (x + 0.25 * w) + " " + y + " L " + (x + 0.75 * w) + " " + y + " L " + (x + w) + " "
							+ (y + 0.5 * h) + " L " + (x + 0.75 * w) + " " + (y + h) + " L " + (x + 0.25 * w) + " "
							+ (y + h) + " L " + x + " " + (y + 0.5 * h);
				}

				elem.setAttribute("d", d + " Z");
			} else if (shape.equals(mxConstants.SHAPE_CLOUD)) {
				elem = document.createElement("path");

				String d = "M " + (x + 0.25 * w) + " " + (y + 0.25 * h) + " C " + (x + 0.05 * w) + " " + (y + 0.25 * h)
						+ " " + x + " " + (y + 0.5 * h) + " " + (x + 0.16 * w) + " " + (y + 0.55 * h) + " C " + x + " "
						+ (y + 0.66 * h) + " " + (x + 0.18 * w) + " " + (y + 0.9 * h) + " " + (x + 0.31 * w) + " "
						+ (y + 0.8 * h) + " C " + (x + 0.4 * w) + " " + (y + h) + " " + (x + 0.7 * w) + " " + (y + h)
						+ " " + (x + 0.8 * w) + " " + (y + 0.8 * h) + " C " + (x + w) + " " + (y + 0.8 * h) + " "
						+ (x + w) + " " + (y + 0.6 * h) + " " + (x + 0.875 * w) + " " + (y + 0.5 * h) + " C " + (x + w)
						+ " " + (y + 0.3 * h) + " " + (x + 0.8 * w) + " " + (y + 0.1 * h) + " " + (x + 0.625 * w) + " "
						+ (y + 0.2 * h) + " C " + (x + 0.5 * w) + " " + (y + 0.05 * h) + " " + (x + 0.3 * w) + " "
						+ (y + 0.05 * h) + " " + (x + 0.25 * w) + " " + (y + 0.25 * h);

				elem.setAttribute("d", d + " Z");
			} else if (shape.equals(mxConstants.SHAPE_ACTOR)) {
				elem = document.createElement("path");
				double width3 = w / 3;

				String d = " M " + x + " " + (y + h) + " C " + x + " " + (y + 3 * h / 5) + " " + x + " "
						+ (y + 2 * h / 5) + " " + (x + w / 2) + " " + (y + 2 * h / 5) + " C " + (x + w / 2 - width3)
						+ " " + (y + 2 * h / 5) + " " + (x + w / 2 - width3) + " " + y + " " + (x + w / 2) + " " + y
						+ " C " + (x + w / 2 + width3) + " " + y + " " + (x + w / 2 + width3) + " " + (y + 2 * h / 5)
						+ " " + (x + w / 2) + " " + (y + 2 * h / 5) + " C " + (x + w) + " " + (y + 2 * h / 5) + " "
						+ (x + w) + " " + (y + 3 * h / 5) + " " + (x + w) + " " + (y + h);

				elem.setAttribute("d", d + " Z");
			} else if (shape.equals(mxConstants.SHAPE_CYLINDER)) {
				elem = document.createElement("g");
				background = document.createElement("path");

				double dy = Math.min(40, Math.floor(h / 5));
				String d = " M " + x + " " + (y + dy) + " C " + x + " " + (y - dy / 3) + " " + (x + w) + " "
						+ (y - dy / 3) + " " + (x + w) + " " + (y + dy) + " L " + (x + w) + " " + (y + h - dy) + " C "
						+ (x + w) + " " + (y + h + dy / 3) + " " + x + " " + (y + h + dy / 3) + " " + x + " "
						+ (y + h - dy);
				background.setAttribute("d", d + " Z");
				elem.appendChild(background);

				Element foreground = document.createElement("path");
				d = "M " + x + " " + (y + dy) + " C " + x + " " + (y + 2 * dy) + " " + (x + w) + " " + (y + 2 * dy)
						+ " " + (x + w) + " " + (y + dy);

				foreground.setAttribute("d", d);
				foreground.setAttribute("fill", "none");
				foreground.setAttribute("stroke", strokeColor);
				foreground.setAttribute("stroke-width", String.valueOf(strokeWidth));

				elem.appendChild(foreground);
			} else if (getShape(shape) != null && getShape(shape).getSvg() != null) {
				// FIXED use loaded shapes
				Element svg = getShape(shape).getSvg();
				NodeList children = ((Element) svg).getChildNodes();
				int wSvg = Integer.parseInt(svg.getAttribute("width"));
				int hSvg = Integer.parseInt(svg.getAttribute("height"));

				double scalew = w / wSvg;
				double scaleh = h / hSvg;

				// System.out.println(shape + ": " + w + "/" + wSvg + "=" +
				// scalew + "-" + h + "/" + hSvg + "=" + scaleh);
				elem = document.createElement("g");
				String transform = "translate(" + (x) + "," + (y) + ")";
				elem.setAttribute("transform", transform);
				elem.setAttribute("x", String.valueOf(x));
				elem.setAttribute("y", String.valueOf(y));
				elem.setAttribute("width", String.valueOf(w));
				elem.setAttribute("height", String.valueOf(h));
				for (int j = 0; j < children.getLength(); j++) {
					if (children.item(j) instanceof Element == false)
						continue;
					Element item = (Element) children.item(j);
					String[] names = item.getTagName().split(":");
					Element e = document.createElement(names[1]);
					NamedNodeMap atts = item.getAttributes();
					for (int i = 0; i < atts.getLength(); i++) {
						String attName = atts.item(i).getNodeName();
						String attValue = atts.item(i).getNodeValue();
						e.setAttribute(attName, attValue);
					}
					transform = "scale(" + scalew + "," + scaleh + ")";
					e.setAttribute("transform", transform);
					e.setAttribute("vector-effect", "non-scaling-stroke");
					elem.appendChild(e);
				}
			} else {
				background = document.createElement("rect");
				elem = background;
				elem.setAttribute("x", String.valueOf(x));
				elem.setAttribute("y", String.valueOf(y));
				elem.setAttribute("width", String.valueOf(w));
				elem.setAttribute("height", String.valueOf(h));
				if (mxUtils.isTrue(style, mxConstants.STYLE_ROUNDED, false)) {
					String r = String.valueOf(Math.min(w * mxConstants.RECTANGLE_ROUNDING_FACTOR,
							h * mxConstants.RECTANGLE_ROUNDING_FACTOR));
					elem.setAttribute("rx", r);
					elem.setAttribute("ry", r);
				}

				// Paints the label image
				if (shape.equals(mxConstants.SHAPE_LABEL)) {
					String img = getImageForStyle(style);

					if (img != null) {
						String imgAlign = mxUtils.getString(style, mxConstants.STYLE_IMAGE_ALIGN,
								mxConstants.ALIGN_LEFT);
						String imgValign = mxUtils.getString(style, mxConstants.STYLE_IMAGE_VERTICAL_ALIGN,
								mxConstants.ALIGN_MIDDLE);
						int imgWidth = (int) (mxUtils.getInt(style, mxConstants.STYLE_IMAGE_WIDTH,
								mxConstants.DEFAULT_IMAGESIZE) * scale);
						int imgHeight = (int) (mxUtils.getInt(style, mxConstants.STYLE_IMAGE_HEIGHT,
								mxConstants.DEFAULT_IMAGESIZE) * scale);
						int spacing = (int) (mxUtils.getInt(style, mxConstants.STYLE_SPACING, 2) * scale);

						mxRectangle imageBounds = new mxRectangle(x, y, w, h);

						if (imgAlign.equals(mxConstants.ALIGN_CENTER)) {
							imageBounds.setX(imageBounds.getX() + (imageBounds.getWidth() - imgWidth) / 2);
						} else if (imgAlign.equals(mxConstants.ALIGN_RIGHT)) {
							imageBounds.setX(imageBounds.getX() + imageBounds.getWidth() - imgWidth - spacing - 2);
						} else
						// LEFT
						{
							imageBounds.setX(imageBounds.getX() + spacing + 4);
						}

						if (imgValign.equals(mxConstants.ALIGN_TOP)) {
							imageBounds.setY(imageBounds.getY() + spacing);
						} else if (imgValign.equals(mxConstants.ALIGN_BOTTOM)) {
							imageBounds.setY(imageBounds.getY() + imageBounds.getHeight() - imgHeight - spacing);
						} else
						// MIDDLE
						{
							imageBounds.setY(imageBounds.getY() + (imageBounds.getHeight() - imgHeight) / 2);
						}

						imageBounds.setWidth(imgWidth);
						imageBounds.setHeight(imgHeight);

						elem = document.createElement("g");
						elem.appendChild(background);

						Element imageElement = createImageElement(imageBounds.getX(), imageBounds.getY(),
								imageBounds.getWidth(), imageBounds.getHeight(), img, false, false, false,
								isEmbedded());

						if (opacity != 100 || fillOpacity != 100) {
							String value = String.valueOf(opacity * fillOpacity / 10000);
							imageElement.setAttribute("opacity", value);
						}

						elem.appendChild(imageElement);
					}

					// Paints the glass effect
					if (mxUtils.isTrue(style, mxConstants.STYLE_GLASS, false)) {
						double size = 0.4;

						// TODO: Mask with rectangle or rounded rectangle of
						// label
						// Creates glass overlay
						Element glassOverlay = document.createElement("path");

						// LATER: Not sure what the behaviour is for mutiple SVG
						// elements in page.
						// Probably its possible that this points to an element
						// in another SVG
						// node which when removed will result in an undefined
						// background.
						glassOverlay.setAttribute("fill", "url(#" + getGlassGradientElement().getAttribute("id") + ")");

						String d = "m " + (x - strokeWidth) + "," + (y - strokeWidth) + " L " + (x - strokeWidth) + ","
								+ (y + h * size) + " Q " + (x + w * 0.5) + "," + (y + h * 0.7) + " "
								+ (x + w + strokeWidth) + "," + (y + h * size) + " L " + (x + w + strokeWidth) + ","
								+ (y - strokeWidth) + " z";
						glassOverlay.setAttribute("stroke-width", String.valueOf(strokeWidth / 2));
						glassOverlay.setAttribute("d", d);
						elem.appendChild(glassOverlay);
					}
				}
			}

			double rotation = mxUtils.getDouble(style, mxConstants.STYLE_ROTATION);
			int cx = x + w / 2;
			int cy = y + h / 2;

			Element bg = background;

			if (bg == null) {
				bg = elem;
			}

			if (!bg.getNodeName().equalsIgnoreCase("use") && !bg.getNodeName().equalsIgnoreCase("image")) {
				if (!fillColor.equalsIgnoreCase("none") && !gradientColor.equalsIgnoreCase("none")) {
					String direction = mxUtils.getString(style, mxConstants.STYLE_GRADIENT_DIRECTION);
					Element gradient = getGradientElement(fillColor, gradientColor, direction);

					if (gradient != null) {
						bg.setAttribute("fill", "url(#" + gradient.getAttribute("id") + ")");
					}
				} else {
					bg.setAttribute("fill", fillColor);
				}

				bg.setAttribute("stroke", strokeColor);
				bg.setAttribute("stroke-width", String.valueOf(strokeWidth));

				// Adds the shadow element
				Element shadowElement = null;

				if (mxUtils.isTrue(style, mxConstants.STYLE_SHADOW, false) && !fillColor.equals("none")) {
					shadowElement = (Element) bg.cloneNode(true);

					shadowElement.setAttribute("transform", mxConstants.SVG_SHADOWTRANSFORM);
					shadowElement.setAttribute("fill", mxConstants.W3C_SHADOWCOLOR);
					shadowElement.setAttribute("stroke", mxConstants.W3C_SHADOWCOLOR);
					shadowElement.setAttribute("stroke-width", String.valueOf(strokeWidth));

					if (rotation != 0) {
						shadowElement.setAttribute("transform",
								"rotate(" + rotation + "," + cx + "," + cy + ") " + mxConstants.SVG_SHADOWTRANSFORM);
					}

					if (opacity != 100) {
						String value = String.valueOf(opacity / 100);
						shadowElement.setAttribute("fill-opacity", value);
						shadowElement.setAttribute("stroke-opacity", value);
					}

					appendSvgElement(shadowElement);
				}
			}

			if (rotation != 0) {
				elem.setAttribute("transform",
						elem.getAttribute("transform") + " rotate(" + rotation + "," + cx + "," + cy + ")");

			}

			if (opacity != 100 || fillOpacity != 100 || strokeOpacity != 100) {
				String fillValue = String.valueOf(opacity * fillOpacity / 10000);
				String strokeValue = String.valueOf(opacity * strokeOpacity / 10000);
				elem.setAttribute("fill-opacity", fillValue);
				elem.setAttribute("stroke-opacity", strokeValue);
			}

			if (mxUtils.isTrue(style, mxConstants.STYLE_DASHED)) {
				String pattern = mxUtils.getString(style, mxConstants.STYLE_DASH_PATTERN, "3, 3");
				elem.setAttribute("stroke-dasharray", pattern);
			}

			appendSvgElement(elem);

			return elem;
		}

	}

	public static void putShape(String name, mxStencilShapeExtended shape) {
		shapes.put(name, shape);
	}

	public static mxStencilShapeExtended getShape(String name) {
		mxIShape shape = shapes.get(name);
		return (mxStencilShapeExtended) shape;
	}
	@Override
	protected InputStream getResource(String src)
	{
		InputStream stream = null;

		try
		{
			stream = new BufferedInputStream(new URL(src).openStream());
		}
		catch (Exception e1)
		{
			stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(src);
		}

		return stream;
	}

}