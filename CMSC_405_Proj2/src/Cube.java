import com.jogamp.opengl.*;

/**
 * Based on UnlitCube.java. Uses JOGL to draw a simple cube with each face being a different color.
 * Does not use lighting
 */
public class Cube {

  // -------------------- methods to draw a cube ----------------------

  /** This method creates a square, which will be used to draw the 6 sides of the cube. */
  private static void square(GL2 gl2, double r, double g, double b) {
    gl2.glColor3d(r, g, b);
    gl2.glBegin(GL2.GL_TRIANGLE_FAN);
    gl2.glVertex3d(-0.5, -0.5, 0.5);
    gl2.glVertex3d(0.5, -0.5, 0.5);
    gl2.glVertex3d(0.5, 0.5, 0.5);
    gl2.glVertex3d(-0.5, 0.5, 0.5);
    gl2.glEnd();
  }

  public static void drawCube(GL2 gl2) {
    gl2.glPushMatrix();
    square(gl2, 1, 0, 0); // red front face

    gl2.glPushMatrix();
    gl2.glRotated(90, 0, 1, 0);

    square(gl2, 0, 1, 0); // green right face
    gl2.glPopMatrix();

    gl2.glPushMatrix();
    gl2.glRotated(-90, 1, 0, 0);
    square(gl2, 0, 0, 1); // blue top face
    gl2.glPopMatrix();

    gl2.glPushMatrix();
    gl2.glRotated(180, 0, 1, 0);
    square(gl2, 0, 1, 1); // cyan back face
    gl2.glPopMatrix();

    gl2.glPushMatrix();
    gl2.glRotated(-90, 0, 1, 0);
    square(gl2, 1, 0, 1); // magenta left face
    gl2.glPopMatrix();

    gl2.glPushMatrix();
    gl2.glRotated(90, 1, 0, 0);
    square(gl2, 1, 1, 0); // yellow bottom face
    gl2.glPopMatrix();

    gl2.glPopMatrix(); // Restore matrix to its state before drawCube() was called.
  }
}
