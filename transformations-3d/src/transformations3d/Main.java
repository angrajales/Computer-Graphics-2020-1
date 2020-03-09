package transformations3d;
/**
 * @author Anderson Grajales Alzate
 * @author Stiven Ramírez Arango
 * @date 08/03/2020
 * @version 1.0
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel implements KeyListener {
	// Logging
	private Logger logger = Logger.getLogger(Main.class.getName());
	// Mid Z-Plane
	private double projectionDistance = -500;
	private ArrayList<Point4> points = new ArrayList<Point4>();
	private ArrayList<Edge> edges = new ArrayList<Main.Edge>();
	private double theta = 10.; // grades
	private double move = 10.; // move
	private Graphics2D g2d;

	public Main() {
		this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		char key = e.getKeyChar();
		compile(key);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.BLACK);
		g2d = (Graphics2D) g;
		g2d.setColor(Color.YELLOW);
		var w = getActualWidth();
		var h = getActualHeight();
		g2d.setColor(Color.yellow);
		g2d.drawLine(w / 2, 0, w / 2, h);
		g2d.drawLine(0, h / 2, w, h / 2);
		if(edges.size() == 0) readFile();
		paint();
	}

	private void readFile() {
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader(new File(Constants.FILE_PATH + Constants.FILE_NAME + Constants.FILE_EXTENSION)));
			String line = reader.readLine();
			var n = toInt(line);
			for (int i = 0; i < n; ++i) {
				line = reader.readLine();
				String[] stPoints = line.split(" ");
				var x = toInt(stPoints[0]);
				var y = toInt(stPoints[1]);
				var z = toInt(stPoints[2]);
				var p = new Point4(x, y, z, 1);
				points.add(p);
			}
			var m = toInt(reader.readLine());
			for (int i = 0; i < m; ++i) {
				line = reader.readLine();
				String[] stEdges = line.split(" ");
				var e = new Edge();
				e.p1 = points.get(toInt(stEdges[0]));
				e.p2 = points.get(toInt(stEdges[1]));
				edges.add(e);
				logger.log(Level.INFO, "Reading edge " + e, e);

			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private void paint() {
		var projectionMatrix = Constants.getProjectionMatrix(projectionDistance);
		edges.forEach(edge -> {
			var p1 = edge.p1;
			var p2 = edge.p2;
			var pp1 = Matrix4x4.times(projectionMatrix, p1).normalize();
			var pp2 = Matrix4x4.times(projectionMatrix, p2).normalize();
			var w = getActualWidth();
			var h = getActualHeight();
			g2d.drawLine(w / 2 + toInt(pp1.x),
					h / 2 - toInt(pp1.y),
					w / 2 + toInt(pp2.x),
					h / 2 - toInt(pp2.y));
		});
	}
	
	private void compile(char key) {
		if(key == 'o' || key == 'O') {
			rotateZ(false);
		}
		if(key == 'l' || key == 'L') {
			rotateZ(true);
		}
		if(key == 'u' || key == 'U') {
			rotateX(false);
		}
		if(key == 'j' || key == 'J') {
			rotateX(true);
		}
		if(key == 'i' || key == 'I') {
			rotateY(false);
		}
		if(key == 'k' || key == 'K') {
			rotateY(true);
		}
		if(key == 'a' || key == 'A') {
			translateX(-1);
		}
		if(key == 'd' || key == 'D') {
			translateX(1);
		}
		if(key == 'w' || key == 'w') {
			translateY(1);
		}
		if(key == 's' || key == 's') {
			translateY(-1);
		}
		if(key == 'x' || key == 'X') {
			leavePlane(-1);
		}
		if(key == 'z' || key == 'Z') {
			leavePlane(1);
		}
                if(key == 'y' || key == 'Y') {
                        scale(-1);
                }
                if(key == 'h' || key == 'H') {
                        scale(1);
                }
	}

	private void rotateZ(boolean clockwise) {
		Matrix4x4 mat = null;
		if(clockwise) {
			mat = Constants.getRotationMatrixZ(theta);
		}else {
			mat = Constants.getRotationMatrixZ_(theta);
		}
		doStep(mat, true);
	}
	private void rotateX(boolean clockwise) {
		Matrix4x4 mat = null;
		if(clockwise) {
			mat = Constants.getRotationMatrixX(theta);
		}else {
			mat = Constants.getRotationMatrixX_(theta);
		}
		doStep(mat, true);
	}
	private void rotateY(boolean clockwise) {
		Matrix4x4 mat = null;
		if(clockwise) {
			mat = Constants.getRotationMatrixY(theta);
		}else {
			mat = Constants.getRotationMatrixY_(theta);
		}
		doStep(mat, true);
	}
	private void translateX(double dir) {
		var transX = Constants.getTranslationMatrix(dir * move, 0, 0);
		doStep(transX, false);
	}
	private void translateY(double dir) {
		var transY = Constants.getTranslationMatrix(0, dir * move, 0);
		doStep(transY, false);
	}
	private void leavePlane(double dir) {
		projectionDistance += (dir * move);
		repaint();
	}
        private void scale(double dir) {
                var mat = Constants.getScalingMatrix(dir * move, dir * move, 0);
                doStep(mat, true);
        }

	private void doStep(Matrix4x4 mat, boolean needsPivot) {
		if (needsPivot) {
			var pivot = getPivot();
			doTranslate(pivot, -1.);
			doTransformation(mat);
			doTranslate(pivot, 1.);
		} else {
			doTransformation(mat);
		}
		repaint();
	}
	

	private Point4 getPivot() {
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double minW = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		double maxY = Double.MIN_VALUE;
		double maxW = Double.MIN_VALUE;
		for (var edge : edges) {
			minX = Math.min(minX, edge.p1.x);
			minX = Math.min(minX, edge.p2.x);
			minY = Math.min(minY, edge.p1.y);
			minY = Math.min(minY, edge.p2.y);
			minW = Math.min(minW, edge.p1.w);
			minW = Math.min(minW, edge.p2.w);
			maxX = Math.max(maxX, edge.p1.x);
			maxX = Math.max(maxX, edge.p2.x);
			maxY = Math.max(maxY, edge.p1.y);
			maxY = Math.max(maxY, edge.p2.y);
			maxW = Math.max(maxW, edge.p1.w);
			maxW = Math.max(maxW, edge.p2.w);
		}
		System.out.println(maxY + " " + minY);
		Point4 res = new Point4(maxX - (maxX - minX) / 2, maxY - (maxY - minY) / 2, maxW - (maxW - minW) / 2, 1);
		System.out.println(res);
		return res;
	}

	private void doTranslate(Point4 pivot, double reverse) {
		var tx = pivot.x * reverse;
		var ty = pivot.y * reverse;
		var tw = pivot.w * reverse;
		var translationMatrix = Constants.getTranslationMatrix(tx, ty, tw);
		edges.forEach(edge -> {
			var transP1 = Matrix4x4.times(translationMatrix, edge.p1);
			var transP2 = Matrix4x4.times(translationMatrix, edge.p2);
			edge.p1 = transP1;
			edge.p2 = transP2;
		});	
	}

	private void doTransformation(Matrix4x4 mat) {
		edges.forEach(edge -> {
			var transP1 = Matrix4x4.times(mat, edge.p1);
			var transP2 = Matrix4x4.times(mat, edge.p2);
			edge.p1 = transP1;
			edge.p2 = transP2;
		});
	}

	private int toInt(Object obj) {
		if (obj instanceof Double) {
			return (int) ((Double) obj).intValue();
		} else if (obj instanceof String) {
			return Integer.parseInt((String) obj);
		} else {
			throw new RuntimeException("Type is not valid");
		}
	}

	class Edge {
		Point4 p1, p2;

		@Override
		public String toString() {
			String format = "(%.0f, %.0f, %.0f) -> (%.0f, %.0f, %.0f)";
			return String.format(format, p1.x, p1.y, p1.z, p2.x, p2.y, p2.z);
		}
	}

	public static void main(String[] args) {
		var frame = new JFrame(Constants.TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new Main());
		frame.setSize(Constants.MAX_WIDTH, Constants.MAX_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private int getActualWidth() {
		var size = getSize();
		var insets = getInsets();
		var w = size.width - insets.left - insets.right;
		return w;
	}

	public int getActualHeight() {
		var size = getSize();
		var insets = getInsets();
		var h = size.height - insets.top - insets.bottom;
		return h;
	}
}
