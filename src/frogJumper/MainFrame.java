package frogJumper;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MainFrame implements KeyListener,Runnable {
	static JFrame f;
	static Frog frog;
	static Car car;
	static Fly fly,flySup;
	static JLabel Score;
	static int score,Hiscore=0;
	Thread t=new Thread(this);
	JLabel bg = new JLabel();

	public static void main(String[] args) {
		new MainFrame();
	}

	@SuppressWarnings("deprecation")
	public MainFrame() {
		f = new JFrame("Frog Jumper");
		frog = new Frog();
		car = new Car();
		flySup = new Fly(true);
		fly = new Fly(false);
		Score = new JLabel();
		score = 0;
		t.start();
		
		ImageIcon img = new ImageIcon(this.getClass().getResource("/bgRoad.png"));
		f.setBounds(500, 300, 900, 500);
		f.setCursor(JFrame.HAND_CURSOR);
		f.setUndecorated(true);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setBackground(Color.BLACK);
		f.setLayout(null);
		f.addKeyListener(this);
		f.setFocusable(true);

		bg.setIcon(img);
		bg.setBounds(0, 0, 900, 500);
		frog.setBounds(f.getWidth() / 2 - frog.width / 2, f.getHeight() - 50 - frog.height, frog.width, frog.height);

		car.SetRight();
		car.setBounds(-71, f.getHeight() / 2 - car.height / 2, car.width, car.height);
		car.moveCar.start();
		
		flySup.setBounds(-80,-80,flySup.width, flySup.height);
		flySup.StartFly.start();
		
		fly.setPosUp();
		fly.StartFly.start();
		
		

		Font myFont = new Font("Consolas", Font.BOLD, 18);
		Score.setText("Score: " + score+"  Hi: "+Hiscore);
		Score.setForeground(Color.white);
		Score.setBounds(f.getWidth() * 1 / 2, 0, 400, 40);
		Score.setFont(myFont);
		
		f.add(Score);
		f.add(frog);
		f.add(car);
		f.add(fly);
		f.add(flySup);
		f.add(bg);
		f.setVisible(true);

	}

	public static synchronized void playSound(final String name) {
		new Thread(new Runnable() {
			// The wrapper thread is unnecessary, unless it blocks on the
			// Clip finishing; see comments.
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem
							.getAudioInputStream(MainFrame.class.getResourceAsStream("/" + name));
					clip.open(inputStream);

					clip.start();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}

	public void exit() {
		int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to Quit ?", "QUIT?",
				JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION) {
			f.dispose();
			System.exit(0);
		}
	}

	private void restart() {
		f.dispose();
		if(score>Hiscore)
			Hiscore=score;
		score = 0;
		Frog.up = false;
		Frog.down = false;
		Frog.alive = true;
		new MainFrame();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {

		int code = arg0.getKeyCode();
		// left 37
		// right 39
		if (code == 37 && frog.getX() - 10 > 0 && Frog.alive) {

			frog.setBounds(frog.getX() - 10, frog.getY(), frog.width, frog.height);
		}
		if (code == 39 && frog.getX() + 10 < f.getWidth() - frog.width && Frog.alive) {

			frog.setBounds(frog.getX() + 10, frog.getY(), frog.width, frog.height);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int code = arg0.getKeyCode();
		// if code = 27 then escape key detected
		// up 38
		// down 40
		if (code == 32 && !Frog.alive) {//space
			restart();
		}
		if (code == 27) {
			exit();
		}
		if (code == 38) {
			if (!Frog.down && Frog.alive) {
				playSound("frogJump.wav");
				Frog.up = true;
			}
		}
		if (code == 40) {
			if (!Frog.up && Frog.alive) {
				playSound("frogJump.wav");
				Frog.down = true;
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		while(Frog.alive){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			if(score>=10){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
				score--;
				Score.setText("Score: " + score+"  Hi: "+Hiscore);
			}
		}
	}

}
