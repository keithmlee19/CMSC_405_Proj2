import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;

/**
 * Based on UnlitCube.java and IFSPolyhedronViewer.java from the course resources. JOGL application
 * that supports 3D shapes and keyboard event handling
 */
@SuppressWarnings("serial")
public class MainScene extends GLJPanel implements GLEventListener, KeyListener {
  private double rotateX = 15; // default rotations of the shape about the axes
  private double rotateY = -15;
  private double rotateZ = 0;
  private double translateX = 0; // default transforms in each direction
  private double translateY = 0;
  private double translateZ = 0;
  private double scale = 1; // default image scale

  /**
   * A main routine to create and show a window that contains a panel of type MainScene. The program
   * ends when the user closes the window.
   */
  public static void main(String[] args) {
    JFrame window = new JFrame("Project 2 - JOGL 3D Shapes");
    MainScene panel = new MainScene();
    window.setContentPane(panel);
    window.pack();
    window.setLocation(50, 50);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setVisible(true);
    panel.requestFocusInWindow();
  }

  /** Constructor for class MainScene. */
  public MainScene() {
    super(new GLCapabilities(null)); // Makes a panel with default OpenGL "capabilities".
    setPreferredSize(new Dimension(640, 480)); // changed dimensions to fit specifications
    addGLEventListener(
        this); // A listener is essential! The listener is where the OpenGL programming lives.
    addKeyListener(this);
  }

  // ----------------  Method to draw the polyhedrons  --------------
  public void drawPolyhedron(
      GL2 gl2, Polyhedron polyhedron, double ps, double xt, double yt, double zt) {
    if (polyhedron.faceColors == null) {
      // Make up random face colors.
      polyhedron.faceColors = new double[polyhedron.faces.length][];
      for (int i = 0; i < polyhedron.faceColors.length; i++) {
        double[] rgb = {Math.random(), Math.random(), Math.random()};
        polyhedron.faceColors[i] = rgb;
      }
    }

    gl2.glPushMatrix();
    gl2.glScaled(ps, ps, ps); // scale to fit nicely in window
    gl2.glTranslated(xt, yt, zt);
    int i, j;
    for (i = 0; i < polyhedron.faces.length; i++) {
      gl2.glColor3dv(polyhedron.faceColors[i], 0);
      gl2.glBegin(GL2.GL_TRIANGLE_FAN);
      for (j = 0; j < polyhedron.faces[i].length; j++) {
        int vertexNum = polyhedron.faces[i][j];
        gl2.glVertex3dv(polyhedron.vertices[vertexNum], 0);
      }
      gl2.glEnd();
    }
    gl2.glPopMatrix();
  }

  // -------------------- GLEventListener Methods -------------------------

  /**
   * The display method is called when the panel needs to be redrawn. The is where the code goes for
   * drawing the image, using OpenGL commands.
   */
  public void display(GLAutoDrawable drawable) {

    GL2 gl2 = drawable.getGL().getGL2(); // The object that contains all the OpenGL methods.

    gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

    gl2.glLoadIdentity(); // Set up modelview transforms.
    gl2.glRotated(rotateZ, 0, 0, 1);
    gl2.glRotated(rotateY, 0, 1, 0);
    gl2.glRotated(rotateX, 1, 0, 0);
    gl2.glScaled(scale, scale, scale);
    gl2.glTranslated(translateX, translateY, translateZ);

    // draw cube
    Cube.drawCube(gl2);
    // draw polyhedrons
    drawPolyhedron(gl2, Polyhedron.icosahedron, 0.7, -3, 2, 1);
    drawPolyhedron(gl2, Polyhedron.soccerBall, 0.9, 3, 2, 0);
    drawPolyhedron(gl2, Polyhedron.stellatedDodecahedron, 1, -3, -2, -1);
    drawPolyhedron(gl2, Polyhedron.tetrahedron, 0.5, 0, -4, 0);
    drawPolyhedron(gl2, Polyhedron.truncatedRhombicDodecahedron, 0.6, 4, -4, 0);
  } // end display()

  public void init(GLAutoDrawable drawable) {
    // called when the panel is created
    GL2 gl2 = drawable.getGL().getGL2();
    gl2.glMatrixMode(GL2.GL_PROJECTION);
    // gl2.glOrtho(-1, 1 ,-1, 1, -1, 1);
    // Changing this is your coordinate -x,x,-y,y,-z,z)
    // Larger numbers zooms out.
    // Play with this to make sure you see your shapes.
    gl2.glOrtho(-5, 5, -5, 5, -5, 5);
    gl2.glMatrixMode(GL2.GL_MODELVIEW);
    gl2.glClearColor(0, 0, 0, 1);
    gl2.glEnable(GL2.GL_DEPTH_TEST);
  }

  public void dispose(GLAutoDrawable drawable) {
    // called when the panel is being disposed
  }

  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    // called when user resizes the window
  }

  // ----------------  Methods from the KeyListener interface --------------

  public void keyPressed(KeyEvent evt) {
    int key = evt.getKeyCode();
    if (key == KeyEvent.VK_LEFT) rotateY -= 15;
    else if (key == KeyEvent.VK_RIGHT) rotateY += 15;
    else if (key == KeyEvent.VK_DOWN) rotateX -= 15;
    else if (key == KeyEvent.VK_UP) rotateX += 15;
    else if (key == KeyEvent.VK_PAGE_UP) scale += 0.1;
    else if (key == KeyEvent.VK_PAGE_DOWN) scale -= 0.1;
    else if (key == KeyEvent.VK_HOME) {
      rotateX = 15;
      rotateY = -15;
      rotateZ = 0;
      scale = 1;
    }
    repaint();
  }

  public void keyReleased(KeyEvent evt) {}

  public void keyTyped(KeyEvent evt) {}
}
