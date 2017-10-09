import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class CSVToXMLConverter
{

	static JFrame jf_JFrame;
	static JPanel jp_JPanel;
	static GridBagLayout gbl_GridBagLayout;
	static GridBagConstraints gbc_GridBagConstraints;
	static JLabel jl_csvFile;
	static JTextField jtf_csvFile;
	static JButton jb_fileChooser;
	static JLabel jl_delimiter;
	static JTextField jtf_delimiter;
	static String string_delimiter;
	static JLabel jl_rootTag;
	static JTextField jtf_rootTag;
	static JLabel jl_newGroupTag;
	static JTextField jtf_newGroupTag;
	static JLabel jl_groupTagsLineLayout;
	static JTextField jtf_groupTagsLineLayout;
	static JButton jb_convert;
	static JLabel jl_progress;
	static JTextField jtf_progress;
	static int int_currentLineNumber;
	static JFileChooser jfc_JFileChooser;
	static File file_fileChoosed;
	static File file_fileToBeSaved;
	static String string_currentLineText;
	static int int_numberOfTags;
	static String[] stringArray_tagNames;
	static String string_currentTag;
	static String[] stringArray_currentLineData;
	static int int_currentTableIndex;
	static int int_maximumArrayLength;
	static int int_delimitersDifference;
	static Thread thread_runThread;

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		initialiseGUI();
		initialiseFunctionality();
	}

	public static void initialiseGUI()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
		{
			Logger.getLogger(CSVToXMLConverter.class.getName()).log(Level.SEVERE, null, ex);
		}

		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		jf_JFrame = new JFrame();
		jf_JFrame.setTitle("CSV to XML Converter");

		jp_JPanel = new JPanel();
		gbl_GridBagLayout = new GridBagLayout();
		gbc_GridBagConstraints = new GridBagConstraints();
		gbc_GridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		jp_JPanel.setLayout(gbl_GridBagLayout);

		jl_csvFile = new JLabel(" CSV file:");
		gbc_GridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbc_GridBagConstraints.gridx = 0;
		gbc_GridBagConstraints.weightx = 1;
		gbc_GridBagConstraints.gridwidth = 1;
		gbc_GridBagConstraints.gridy = 0;
		gbc_GridBagConstraints.weighty = 1;
		jp_JPanel.add(jl_csvFile, gbc_GridBagConstraints);

		jtf_csvFile = new JTextField("Select a CSV file...");
		gbc_GridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbc_GridBagConstraints.gridx = 1;
		gbc_GridBagConstraints.gridwidth = 2;
		gbc_GridBagConstraints.weightx = 1;
		gbc_GridBagConstraints.gridy = 0;
		gbc_GridBagConstraints.weighty = 1;
		jp_JPanel.add(jtf_csvFile, gbc_GridBagConstraints);

		jb_fileChooser = new JButton("...");
		gbc_GridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbc_GridBagConstraints.gridx = 3;
		gbc_GridBagConstraints.gridwidth = 1;
		gbc_GridBagConstraints.weightx = 1;
		gbc_GridBagConstraints.gridy = 0;
		gbc_GridBagConstraints.weighty = 1;
		jp_JPanel.add(jb_fileChooser, gbc_GridBagConstraints);
		
		jl_delimiter = new JLabel(" Delimiter:");
		gbc_GridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbc_GridBagConstraints.gridx = 0;
		gbc_GridBagConstraints.gridwidth = 1;
		gbc_GridBagConstraints.weightx = 1;
		gbc_GridBagConstraints.gridy = 1;
		gbc_GridBagConstraints.weighty = 1;
		jp_JPanel.add(jl_delimiter, gbc_GridBagConstraints);
		
		jtf_delimiter = new JTextField(",");gbc_GridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbc_GridBagConstraints.gridx = 1;
		gbc_GridBagConstraints.gridwidth = 3;
		gbc_GridBagConstraints.weightx = 1;
		gbc_GridBagConstraints.gridy = 1;
		gbc_GridBagConstraints.weighty = 1;
		jp_JPanel.add(jtf_delimiter, gbc_GridBagConstraints);

		jl_rootTag = new JLabel(" Name of the root tag:");
		gbc_GridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbc_GridBagConstraints.gridx = 0;
		gbc_GridBagConstraints.gridwidth = 1;
		gbc_GridBagConstraints.weightx = 1;
		gbc_GridBagConstraints.gridy = 2;
		gbc_GridBagConstraints.weighty = 1;
		jp_JPanel.add(jl_rootTag, gbc_GridBagConstraints);

		jtf_rootTag = new JTextField("root");gbc_GridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbc_GridBagConstraints.gridx = 1;
		gbc_GridBagConstraints.gridwidth = 3;
		gbc_GridBagConstraints.weightx = 1;
		gbc_GridBagConstraints.gridy = 2;
		gbc_GridBagConstraints.weighty = 1;
		jp_JPanel.add(jtf_rootTag, gbc_GridBagConstraints);

		jl_newGroupTag = new JLabel(" Name of a new group tag:");
		gbc_GridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbc_GridBagConstraints.gridx = 0;
		gbc_GridBagConstraints.gridwidth = 1;
		gbc_GridBagConstraints.weightx = 1;
		gbc_GridBagConstraints.gridy = 3;
		gbc_GridBagConstraints.weighty = 1;
		jp_JPanel.add(jl_newGroupTag, gbc_GridBagConstraints);

		jtf_newGroupTag = new JTextField("customer");gbc_GridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbc_GridBagConstraints.gridx = 1;
		gbc_GridBagConstraints.gridwidth = 3;
		gbc_GridBagConstraints.weightx = 1;
		gbc_GridBagConstraints.gridy = 3;
		gbc_GridBagConstraints.weighty = 1;
		jp_JPanel.add(jtf_newGroupTag, gbc_GridBagConstraints);
		
		jl_groupTagsLineLayout = new JLabel(" Group tags line layout (same/new):");
		gbc_GridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbc_GridBagConstraints.gridx = 0;
		gbc_GridBagConstraints.gridwidth = 1;
		gbc_GridBagConstraints.weightx = 1;
		gbc_GridBagConstraints.gridy = 4;
		gbc_GridBagConstraints.weighty = 1;
		jp_JPanel.add(jl_groupTagsLineLayout, gbc_GridBagConstraints);

		jtf_groupTagsLineLayout = new JTextField("same");
		gbc_GridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbc_GridBagConstraints.gridx = 1;
		gbc_GridBagConstraints.gridwidth = 3;
		gbc_GridBagConstraints.weightx = 1;
		gbc_GridBagConstraints.gridy = 4;
		gbc_GridBagConstraints.weighty = 1;
		jp_JPanel.add(jtf_groupTagsLineLayout, gbc_GridBagConstraints);
		
		jb_convert = new JButton("Convert");
		gbc_GridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbc_GridBagConstraints.gridx = 0;
		gbc_GridBagConstraints.gridwidth = 2;
		gbc_GridBagConstraints.weightx = 1;
		gbc_GridBagConstraints.gridy = 5;
		gbc_GridBagConstraints.weighty = 4;
		jp_JPanel.add(jb_convert, gbc_GridBagConstraints);

		jl_progress = new JLabel("Progress:");
		gbc_GridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbc_GridBagConstraints.gridx = 2;
		gbc_GridBagConstraints.gridwidth = 1;
		gbc_GridBagConstraints.weightx = 1;
		gbc_GridBagConstraints.gridy = 5;
		gbc_GridBagConstraints.weighty = 4;
		jl_progress.setHorizontalAlignment(jl_progress.RIGHT);
		jp_JPanel.add(jl_progress, gbc_GridBagConstraints);

		jtf_progress = new JTextField();
		jtf_progress.setEditable(false);
		gbc_GridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbc_GridBagConstraints.gridx = 3;
		gbc_GridBagConstraints.gridwidth = 1;
		gbc_GridBagConstraints.weightx = 1;
		gbc_GridBagConstraints.gridy = 5;
		gbc_GridBagConstraints.weighty = 1;
		jp_JPanel.add(jtf_progress, gbc_GridBagConstraints);

		jf_JFrame.add(jp_JPanel);

		jf_JFrame.pack();
		jf_JFrame.setSize(500, 165);
		//jf.setResizable(false);
		jf_JFrame.setLocationRelativeTo(null);
		jf_JFrame.setVisible(true);
	}

	public static void initialiseFunctionality()
	{
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

		jf_JFrame.addWindowListener(new WindowListener()
		{
			public void windowOpened(WindowEvent we)
			{
			}

			public void windowIconified(WindowEvent we)
			{
			}

			public void windowDeiconified(WindowEvent we)
			{
			}

			public void windowDeactivated(WindowEvent we)
			{
			}

			public void windowClosing(WindowEvent we)
			{
				quitApplication();
			}

			public void windowClosed(WindowEvent we)
			{
			}

			public void windowActivated(WindowEvent we)
			{
			}
		});

		jb_fileChooser.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				JFileChooser jfc = new JFileChooser();
				FileNameExtensionFilter fnef = new FileNameExtensionFilter(".csv", "csv");
				jfc.setFileFilter(fnef);

				if (jfc.showSaveDialog(jf_JFrame) == JFileChooser.APPROVE_OPTION)
				{
					file_fileChoosed = jfc.getSelectedFile();

					jtf_csvFile.setText(file_fileChoosed.toString());
				}
			}
		});

		jb_convert.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					convertFile();
				} catch (Exception ex)
				{
					Logger.getLogger(CSVToXMLConverter.class.getName()).log(Level.SEVERE, null, ex);
					
					JOptionPane.showMessageDialog(null, "An unknown error occured." + '\n' + "Please review the options and try again.");
				}
			}
		});
	}

	public static void quitApplication()
	{
		System.exit(0);
	}
	
	public static void setIdleMode()
	{
		jtf_csvFile.setEnabled(true);
		jtf_delimiter.setEnabled(true);
		jtf_rootTag.setEnabled(true);
		jtf_newGroupTag.setEnabled(true);
		jtf_groupTagsLineLayout.setEnabled(true);
		jb_fileChooser.setEnabled(true);
		jb_convert.setEnabled(true);
		jtf_progress.setText(null);
	}
	
	public static void setRunMode()
	{
		jtf_csvFile.setEnabled(false);
		jtf_delimiter.setEnabled(false);
		jtf_rootTag.setEnabled(false);
		jtf_newGroupTag.setEnabled(false);
		jtf_groupTagsLineLayout.setEnabled(false);
		jb_fileChooser.setEnabled(false);
		jb_convert.setEnabled(false);
	}

	public static void convertFile()
	{
		if (jtf_delimiter.getText().equals("") || jtf_delimiter.getText().equals(" ") || jtf_delimiter.getText().equals("	")
		|| jtf_rootTag.getText().equals("") || jtf_rootTag.getText().equals(" ") || jtf_rootTag.getText().equals("	")
		|| jtf_newGroupTag.getText().equals("") || jtf_newGroupTag.getText().equals(" ") || jtf_newGroupTag.getText().equals("	")
		|| jtf_groupTagsLineLayout.getText().equals("") || jtf_groupTagsLineLayout.getText().equals(" ") || jtf_groupTagsLineLayout.getText().equals("	"))
		{
			JOptionPane.showMessageDialog(null, "Make sure the options are correct and try again.");
		}
		else if (!(jtf_groupTagsLineLayout.getText().equals("same") || jtf_groupTagsLineLayout.getText().equals("new") ))
		{
			JOptionPane.showMessageDialog(null, "Make sure you select either \"same\" or \"new\" for the tags line layout.");
		}
		else
		{
			thread_runThread=new Thread()
			{
				public void run()
				{
					setRunMode();
					
					try
					{
						JFileChooser jfc = new JFileChooser();
						FileNameExtensionFilter fnef = new FileNameExtensionFilter(".xml", "xml");
						jfc.setFileFilter(fnef);

						if (jfc.showSaveDialog(jf_JFrame) == JFileChooser.APPROVE_OPTION)
						{
							file_fileToBeSaved = jfc.getSelectedFile();
							
							FileReader fr = new FileReader(jtf_csvFile.getText());
							BufferedReader br = new BufferedReader(fr);

							FileWriter fw = new FileWriter(file_fileToBeSaved + ".xml");
							BufferedWriter bw = new BufferedWriter(fw);
							{
								bw.append("<?xml version=\"1.0\"?>" + '\n');
								bw.append("<" + jtf_rootTag.getText() + ">" + '\n');
								
								string_currentLineText = br.readLine();
								int_currentLineNumber = 0;

								string_delimiter = jtf_delimiter.getText();
								stringArray_tagNames = string_currentLineText.split(string_delimiter, -1);

								string_currentLineText = br.readLine();
								while (string_currentLineText != null && !string_currentLineText.equals(""))
								{
									int_currentLineNumber++;
									stringArray_currentLineData = string_currentLineText.split(string_delimiter, -1);

									bw.append('\t' + "<" + jtf_newGroupTag.getText() + ">");
									if(jtf_groupTagsLineLayout.getText().equals("new"))
									{
										bw.append('\n');
									}
									
									int_maximumArrayLength=stringArray_tagNames.length + Math.abs(stringArray_tagNames.length - stringArray_currentLineData.length);
									
									for (int_currentTableIndex=0; int_currentTableIndex < int_maximumArrayLength; int_currentTableIndex++)
									{
										if(jtf_groupTagsLineLayout.getText().equals("same"))
										{
											if(stringArray_currentLineData[int_currentTableIndex]==null || stringArray_currentLineData[int_currentTableIndex].equals(""))
											{
												bw.append("<" + (stringArray_tagNames[int_currentTableIndex].replaceAll("\"", "")) + " />");
											}
											else
											{
												bw.append("<" + (stringArray_tagNames[int_currentTableIndex].replaceAll("\"", "")) + ">" + (stringArray_currentLineData[int_currentTableIndex].replaceAll("\"", "")) + "</" + (stringArray_tagNames[int_currentTableIndex].replaceAll("\"", "")) + ">");
											}
										}
										else if(jtf_groupTagsLineLayout.getText().equals("new"))
										{
											if(stringArray_currentLineData[int_currentTableIndex]==null || stringArray_currentLineData[int_currentTableIndex].equals(""))
											{
												bw.append('\t' + "" + '\t' + "<" + (stringArray_tagNames[int_currentTableIndex].replaceAll("\"", "")) + " />" + '\n');
											}
											else
											{
												bw.append('\t' + "" + '\t' + "<" + (stringArray_tagNames[int_currentTableIndex].replaceAll("\"", "")) + ">" + (stringArray_currentLineData[int_currentTableIndex].replaceAll("\"", "")) + "</" + (stringArray_tagNames[int_currentTableIndex].replaceAll("\"", "")) + ">" + '\n');
											}											
										}
									}
									
									if(jtf_groupTagsLineLayout.getText().equals("same"))
									{
										bw.append("</" + jtf_newGroupTag.getText() + ">" + '\n');
									}
									else if(jtf_groupTagsLineLayout.getText().equals("new"))
									{
										bw.append('\t' + "</" + jtf_newGroupTag.getText() + ">" + '\n');	
									}
									
									jtf_progress.setText(String.valueOf(int_currentLineNumber));
									jp_JPanel.updateUI();
									
									string_currentLineText = br.readLine();
								}
								bw.append("</" + jtf_rootTag.getText() + ">");
							}
							
							bw.close();
							JOptionPane.showMessageDialog(null, "File converted successfully." + '\n' + "Total number of records: " + int_currentLineNumber);
						}
					} catch (Exception ex)
					{
						Logger.getLogger(CSVToXMLConverter.class.getName()).log(Level.SEVERE, null, ex);
						
						setIdleMode();
						
						int_delimitersDifference=Math.abs(stringArray_tagNames.length - stringArray_currentLineData.length);
						
						if (stringArray_tagNames.length < stringArray_currentLineData.length)
						{
							JOptionPane.showMessageDialog(null, "Error: " + (int_delimitersDifference) + " extra delimiter(s) found in record " + (int_currentLineNumber) + "." + '\n' + "Remove the extra delimiter(s) and try again.");
						}
						else if(stringArray_tagNames.length > stringArray_currentLineData.length)
						{
							JOptionPane.showMessageDialog(null, "Error: " + (int_delimitersDifference) + " delimiter(s) less found in record " + (int_currentLineNumber) + "." + '\n' + "Add the extra delimiter(s) and try again.");
						}
						else
						{
							JOptionPane.showMessageDialog(null, "An unknown error occured." + '\n' + "Please review the options and try again.");
						}
					}
					
					//thread_runThread.interrupt();
					setIdleMode();
				}
			};
			thread_runThread.start();
		}
	}
}