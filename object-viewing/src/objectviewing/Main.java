package objectviewing;

/**
 * @author Anderson Grajales Alzate
 * @author Stiven Ramírez Arango
 * @date 15/03/2020
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

import static java.lang.Math.*;

public class Main extends JPanel implements KeyListener {
	// Logging
	private Logger logger = Logger.getLogger(Main.class.getName());
	// Mid Z-Plane
	private double projectionDistance = -500;
	private ArrayList<Point4> points = new ArrayList();
	private ArrayList<Edge> edges = new ArrayList();
	private ArrayList<Edge> edgesFixed = new ArrayList();
	private Graphics2D g2d;
	private static double phi = 0.;
	private static double theta = 0.;
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

	private void compile(char key) {
		if(key == 'A' || key == 'a'){
			doMove(true, 1);
		}
		if(key == 'D' || key == 'd'){
			doMove(true, -1);
		}
		if(key == 'W' || key == 'w'){
			doMove(false, 1);
		}
		if (key == 'S' || key == 's') {
			doMove(false, -1);
		}
	}

	private void doMove(boolean b, int i) {
		if(b){
			theta += (Constants.ANGLE_MOVE * i);
		}else{
			if(i > 0){
				phi += (phi >= Constants.MAXPHI ? 0: Constants.ANGLE_MOVE);
			}else{
				phi -= (phi <= -Constants.MAXPHI ? 0: Constants.ANGLE_MOVE);
			}
		}
		focusCamera();
	}
	private void focusCamera(){
		Matrix4x4 uvnMatrix = new Matrix4x4();
		Point4 pivot = getPivot();
		double Rp = Constants.CAMERA_DISTANCE * cos(toRadians(phi));
		double z = Rp * cos(toRadians(theta));
		double x = Rp * sin(toRadians(theta));
		double y = Constants.CAMERA_DISTANCE * sin(toRadians(phi));
		// Find n, u and v
		Point4 p0 = new Point4(pivot.x + x, pivot.y + y, pivot.w + z, 1);
		//Over y axis
		Point4 V = new Point4(0, 1, 0, 1);
		Point4 n = new Point4(x, y, z, 1).normalize();
		Point4 u = Point4.cross(V, n).normalize();
		Point4 v = Point4.cross(n, u);
		uvnMatrix.matrix[0][0] = u.x;
		uvnMatrix.matrix[0][1] = u.y;
		uvnMatrix.matrix[0][2] = u.w;
		uvnMatrix.matrix[0][3] = -Point4.dot(u, p0);
		uvnMatrix.matrix[1][0] = v.x;
		uvnMatrix.matrix[1][1] = v.y;
		uvnMatrix.matrix[1][2] = v.w;
		uvnMatrix.matrix[1][3] = -Point4.dot(v, p0);
		uvnMatrix.matrix[2][0] = n.x;
		uvnMatrix.matrix[2][1] = n.y;
		uvnMatrix.matrix[2][2] = n.w;
		uvnMatrix.matrix[2][3] = -Point4.dot(n, p0);
		uvnMatrix.matrix[3][3] = 1;
		doStep(uvnMatrix);
	}
	private void doStep(Matrix4x4 matrix4x4){
		for(int i = 0; i < edgesFixed.size(); ++i){
			Point4 p41 = edgesFixed.get(i).p1;
			Point4 p42 = edgesFixed.get(i).p2;
			Point4 r1 = Matrix4x4.times(matrix4x4, p41);
			Point4 r2 = Matrix4x4.times(matrix4x4, p42);
			Edge e = new Edge();
			e.p1 = r1;
			e.p2 = r2;
			edges.set(i, e);
		}
		repaint();
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
		//g2d.drawLine(w / 2, 0, w / 2, h);
		//g2d.drawLine(0, h / 2, w, h / 2);
		if (edges.size() == 0) {
			readFile();
		}
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
				edgesFixed.add(e);
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
			var pp1 = Matrix4x4.times(projectionMatrix, p1).normalizep();
			var pp2 = Matrix4x4.times(projectionMatrix, p2).normalizep();
			var w = getActualWidth();
			var h = getActualHeight();

			g2d.drawLine(w / 2 + toInt(pp1.x), h / 2 - toInt(pp1.y), w / 2 + toInt(pp2.x), h / 2 - toInt(pp2.y));
		});

	}

	private Point4 getPivot() {
		double minX = 1_000_000;
		double minY = 1_000_000.;
		double minW = 1_000_000;
		double maxX = -1_000_000;
		double maxY = -1_000_000;
		double maxW = -1_000_000;
		for (var edge : edgesFixed) {
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
		Point4 res = new Point4((maxX - (maxX - minX) / 2), (maxY - (maxY - minY) / 2), (maxW - (maxW - minW) / 2), 1);
		return res;
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
			return String.format(format, p1.x, p1.y, p1.w, p2.x, p2.y, p2.w);
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
