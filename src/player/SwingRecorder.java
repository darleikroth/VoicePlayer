package player;
/*
	This file is part of SwingRecorder version 1.0
	Coded / Copyright 2005 by Bert Szoghy
	Tip of the hat to the command line SimpleAudioRecorder.java by Matthias Pfisterer
	at jsresources.org


	webmaster@quadmore.com
	This program is free software; you can redistribute it and/or modify it under the terms
	of the GNU General Public License version 2 as published by the Free Software Foundation;
	This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
	the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
	See the GNU General Public License for more details. You should have received a copy of
	the GNU General Public License along with this program; if not, write to the
	Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/
import javax.swing.Timer;
import java.io.IOException;
import java.io.File;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioFileFormat;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

public class SwingRecorder extends JPanel
{
	private TargetDataLine m_line;
	private AudioFileFormat.Type m_targetType;
	private AudioInputStream m_audioInputStream;
	private File m_outputFile;
	private Timer timer;
	private JTextField txtEmailHere;

	public SwingRecorder()
	{
		final JButton m_Start;
		final JButton m_Stop;
		JPanel p = new JPanel();
		p.add(new JLabel("Click button to begin recording your voice (microphone must be plugged in!)"));
		add(p);

		p = new JPanel();
		m_Start = new JButton("Click to start recording");
		p.add(m_Start);
		add(p);

		m_Start.setBackground(Color.black);
		m_Start.setForeground(Color.white);

		p = new JPanel();
		m_Stop = new JButton("Stop");
		p.add(m_Stop);
		add(p);
		m_Stop.setVisible(false);

		ActionListener btnStartClicked = new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				//Switch buttons:
				m_Stop.setVisible(true);
				m_Start.setVisible(false);
				GlobalStorage.setStopIsNext(true);
				repaint();

				//New thread
				final SwingWorker worker = new SwingWorker()
				{
					public Object construct()
					{
						//Date now = new Date();
                                               // long nowLong = now.getTime();
						String	strFilename = "audioPass";
						//strFilename = nowLong.toString();
						//strFilename = String.valueOf(nowLong);
						System.out.println("Filename will be..." + strFilename + ".wav");
						File outputFile = new File(strFilename + ".wav");

						// Using PCM 44.1 kHz, 16 bit signed,stereo.
						AudioFormat	audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,16000.0F, 16, 2, 4, 16000.0F, false);

						DataLine.Info	info = new DataLine.Info(TargetDataLine.class, audioFormat);
						TargetDataLine	targetDataLine = null;

						try
						{
							targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
							targetDataLine.open(audioFormat);
						}
						catch (LineUnavailableException e)
						{
							System.out.println("unable to get a recording line");
							e.printStackTrace();
							System.exit(1);
						}

						AudioFileFormat.Type	targetType = AudioFileFormat.Type.WAVE;

						final Recorder recorder = new Recorder(targetDataLine,targetType,outputFile);

						int number = 0;
						System.out.println("Recording...");
						recorder.start();

						timer = new Timer(1000, new ActionListener()
						{
							int d = 0;

							public void actionPerformed(ActionEvent evt)
							{
								if(!(GlobalStorage.isStopIsNext()))
								{
									// Here, the recording is actually stopped.
									recorder.stopRecording();
		 							System.out.println("Recording stopped.");
									timer.stop();
								}
							}
						});
						timer.start();

						return "dummy value";
					}
				};
				worker.start(); //required for SwingWorker 3 Class
			}
		};

		m_Start.addActionListener(btnStartClicked);

		ActionListener btnStopClicked = new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				//Start a new thread to record:
				GlobalStorage.setStopIsNext(false);
				m_Stop.setVisible(false);
				m_Start.setVisible(true);
				repaint();
			}
		};

		m_Stop.addActionListener(btnStopClicked);

		p = new JPanel();
		p.add(new JLabel("Email address where the snapshot image should be sent to:"));
		add(p);

		p = new JPanel();
		txtEmailHere = new JTextField(30);
		p.add(txtEmailHere );
		add(p);
		txtEmailHere .setEditable(true);
	}

	public static void main(String s[])
	{
		 JFrame frame = new JFrame("Talk to the ANDROID");
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setContentPane(new SwingRecorder());
		 frame.setSize(450,180);
		 frame.setLocation(100,100);
		 frame.setVisible(true);
    }

	public void start()
	{
		m_line.start();
	}

	public void stopRecording()
	{
		m_line.stop();
		m_line.close();
	}

	public void run()
	{
		try
		{
			AudioSystem.write(m_audioInputStream,m_targetType,m_outputFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
