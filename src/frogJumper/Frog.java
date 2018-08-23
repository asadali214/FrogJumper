package frogJumper;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Frog extends JLabel implements Runnable {
	ImageIcon frogImg = new ImageIcon(this.getClass().getResource("/frogS.png"));
	ImageIcon frogImg2 = new ImageIcon(this.getClass().getResource("/frogD.png"));
	int width = 81;
	int height = 80;
	static boolean up = false;
	static boolean down = false;
	static boolean alive = true;
	Thread t = new Thread(this);

	public Frog() {
		super();
		setIcon(frogImg);
		t.start();
	}
	public void Dead(){
		setIcon(frogImg2);
	}
	@Override
	public void run() {
		xyz: while (alive) {
			try {
				Thread.sleep(6);
			} catch (InterruptedException e) {
			}
			if (up) {
				if (getY() == MainFrame.car.getY()+2 || getY() < 70) {
					up = false;
					if (getY() < 70)
						continue xyz;
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
					}
				}
				
				setBounds(getX(), getY() - 2, width, height);
				
			}

			if (down) {
				if (getY() == MainFrame.car.getY()-2 || getY() > MainFrame.f.getHeight() - 50 - height) {
					down = false;
					if (getY() > MainFrame.f.getHeight() - 70 - height)
						continue xyz;
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
					}
				}
				setBounds(getX(), getY() + 2, width, height);
				
			}
		}

	}
}
