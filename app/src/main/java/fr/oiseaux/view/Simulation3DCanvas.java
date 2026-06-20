package fr.oiseaux.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import fr.oiseaux.model.Bird;
import fr.oiseaux.model.BirdModel;
import fr.oiseaux.model.BoidsModel;

public class Simulation3DCanvas extends GLJPanel implements GLEventListener {
    private BirdModel model;
    private GLU glu = new GLU();
    private FPSAnimator animator;

    //Camera
    private float rotX = 20f, rotY = 0f;
    private float zoom = 200f;
    private int lastMouseX, lastMouseY;
    private float camX = worldSize/2;
    private float camY = worldSize/2;


    private static float worldSize = 100f;

    public Simulation3DCanvas() {
        super(new GLCapabilities(GLProfile.get(GLProfile.GL2)));
        addGLEventListener(this);
        setOpaque(true);
        setFocusable(true);
        requestFocusInWindow();

        // Mouse rotation
        addMouseListener( new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();
            }
            public void mousePressed(MouseEvent e) {
                lastMouseX = e.getX();
                lastMouseY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                rotY += (e.getX() - lastMouseX) * 0.5f;
                rotX += (e.getY() - lastMouseY) * 0.5f;
                lastMouseX = e.getX();
                lastMouseY = e.getY();
            }
        });

        // Zoom with mouse wheel
        addMouseWheelListener(e -> {
            zoom += e.getWheelRotation() * 5f;
            zoom = Math.max(50f, Math.min(500f, zoom));
        });

        // Start animator
        animator = new FPSAnimator(this, 60);
        animator.start();
    }
    
    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        // Lighting
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glColorMaterial(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE);

        float[] lightPos = { worldSize/2, worldSize/2, worldSize*2, 1f };
        float[] lightColor = { 1f, 1f, 1f, 1f };
        float[] ambient = { 0.3f, 0.3f, 0.3f, 1f };
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, lightColor, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0);

        gl.glClearColor(1f, 1f, 1f, 1f); // dark blue background

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();

        // init camera
        glu.gluLookAt(
            camX, camY, zoom,     // camera position
            camX, camY, 0,     // look at center
            0, 1, 0                     // up vector
        );

       
        gl.glTranslatef(worldSize/2, worldSize/2, 0);
        gl.glRotatef(rotX, 1, 0, 0);
        gl.glRotatef(rotY, 0, 0, 1);
        gl.glTranslatef(-worldSize/2, -worldSize/2, 0);

        // Draw world bounding box
        drawBoundingBox(gl);

        // Draw birds
        if (model != null) {
            boolean isBoids = model instanceof BoidsModel;
            for (Bird b : model.getBirds()) {
                drawBird(gl, b, isBoids);
            }
        }
         // Check that the model contains obstacles
if (model.getObstacles() != null) {
        com.jogamp.opengl.util.gl2.GLUT glut = new com.jogamp.opengl.util.gl2.GLUT();
        for (fr.oiseaux.model.Obstacles obs : model.getObstacles()) {
            gl.glPushMatrix();
            gl.glTranslated(obs.getX(), obs.getY(), obs.getZ());
            gl.glColor3f(1.0f, 0.0f, 0.0f); // Red

            // Choose shape based on type
            switch (obs.getType()) {
                case 0: // CUBE
                    glut.glutSolidCube((float) obs.getSize());
                    break;
                case 1: // SPHERE
                    // Set radius (size / 2) and detail (16 slices)
                    glut.glutSolidSphere((float) obs.getSize() / 2, 16, 16);
                    break;
                case 2: // Cone shape
                    glut.glutSolidCone((float) obs.getSize() * 0.2f, (float) obs.getSize() * 2.5f, 32, 1);
                    break;
            }
            gl.glPopMatrix();
        }
    }
    }

    private void drawBoundingBox(GL2 gl) {
        gl.glDisable(GL2.GL_LIGHTING);
        gl.glColor4f(0.3f, 0.3f, 0.5f, 0.5f);
        float s = worldSize;

        gl.glBegin(GL2.GL_LINES);
            // Bottom face
            gl.glVertex3f(0,0,0); gl.glVertex3f(s,0,0);
            gl.glVertex3f(s,0,0); gl.glVertex3f(s,s,0);
            gl.glVertex3f(s,s,0); gl.glVertex3f(0,s,0);
            gl.glVertex3f(0,s,0); gl.glVertex3f(0,0,0);
            // Top face
            gl.glVertex3f(0,0,s); gl.glVertex3f(s,0,s);
            gl.glVertex3f(s,0,s); gl.glVertex3f(s,s,s);
            gl.glVertex3f(s,s,s); gl.glVertex3f(0,s,s);
            gl.glVertex3f(0,s,s); gl.glVertex3f(0,0,s);
            // Vertical edges
            gl.glVertex3f(0,0,0); gl.glVertex3f(0,0,s);
            gl.glVertex3f(s,0,0); gl.glVertex3f(s,0,s);
            gl.glVertex3f(s,s,0); gl.glVertex3f(s,s,s);
            gl.glVertex3f(0,s,0); gl.glVertex3f(0,s,s);
        gl.glEnd();

        gl.glEnable(GL2.GL_LIGHTING);
    }

    private void drawBird(GL2 gl, Bird b, boolean isBoids) {
        gl.glPushMatrix();

        gl.glTranslatef((float)b.pos.x(), (float)b.pos.y(), (float)b.pos.z());

        // Rotate to face velocity direction
        double vx = b.velocity.x();
        double vy = b.velocity.y();
        double vz = b.velocity.z();

        double angleY = Math.toDegrees(Math.atan2(vx, vz));
        double angleX = Math.toDegrees(-Math.atan2(vy, Math.sqrt(vx*vx + vz*vz)));

        gl.glRotatef((float)angleY, 0, 1, 0);
        gl.glRotatef((float)angleX, 1, 0, 0);

        // Color: blue for Vicsek, red for Boids
        if (isBoids) {
            gl.glColor3f(1.0f, 0.3f, 0.2f);
        } else {
            gl.glColor3f(0.2f, 0.6f, 1.0f);
        }

        // Draw tetrahedron (3D triangle)
        float len = 2f;  // length
        float w = 1f;    // width

        gl.glBegin(GL2.GL_TRIANGLES);
            // Front face (tip)
            gl.glNormal3f(0, 0, 1);
            gl.glVertex3f( 0,  0,  len);  // tip
            gl.glVertex3f(-w, -w, -len);  // rear left bottom
            gl.glVertex3f( w, -w, -len);  // rear right bottom

            // Top face
            gl.glNormal3f(0, 1, 0);
            gl.glVertex3f( 0,  0,  len);  // tip
            gl.glVertex3f(-w,  w, -len);  // rear left top
            gl.glVertex3f( w,  w, -len);  // rear right top

            // Left face
            gl.glNormal3f(-1, 0, 0);
            gl.glVertex3f( 0,  0,  len);  // tip
            gl.glVertex3f(-w, -w, -len);  // rear left bottom
            gl.glVertex3f(-w,  w, -len);  // rear left top

            // Right face
            gl.glNormal3f(1, 0, 0);
            gl.glVertex3f( 0,  0,  len);  // tip
            gl.glVertex3f( w, -w, -len);  // rear right bottom
            gl.glVertex3f( w,  w, -len);  // rear right top

            // Back face
            gl.glNormal3f(0, 0, -1);
            gl.glVertex3f(-w, -w, -len);
            gl.glVertex3f( w, -w, -len);
            gl.glVertex3f( w,  w, -len);
            gl.glVertex3f(-w, -w, -len);
            gl.glVertex3f( w,  w, -len);
            gl.glVertex3f(-w,  w, -len);
        gl.glEnd();

        gl.glPopMatrix();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        animator.stop();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, w, h);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0, (double)w/h, 0.1, 1000.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    public void translate(float dx, float dy) {
        camX += dx;
        camY += dy;
    }


    public void setModel(BirdModel mdl) {
        this.model = mdl;
    }
}
