package Java;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class GUI {
	JFrame frame = new JFrame();
	JLabel label = new JLabel("Currency Converter");
	JPanel panel = new JPanel();
	JTextField text1 = new JTextField("0.00");
	JTextField text2 = new JTextField("0.00");
	String[] worldCurrencies = {
		    "AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN",
		    "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BRL",
		    "BSD", "BTN", "BWP", "BYN", "BZD", "CAD", "CDF", "CHF", "CLP", "CNY",
		    "COP", "CRC", "CUP", "CVE", "CZK", "DJF", "DKK", "DOP", "DZD", "EGP",
		    "ERN", "ETB", "EUR", "FJD", "FKP", "FOK", "FYN", "GEL", "GGP", "GHC",
		    "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG", "HUF",
		    "IDR", "ILS", "IMP", "INR", "IQD", "IRR", "ISK", "JEP", "JMD", "JOD",
		    "JPY", "KES", "KGS", "KHR", "KID", "KIP", "KRW", "KWD", "KYD", "KZT",
		    "LAK", "LBP", "LKR", "LRD", "LSL", "LYD", "MAD", "MDL", "MGA", "MKD",
		    "MMK", "MNT", "MOP", "MRU", "MUR", "MVR", "MWK", "MXN", "MYR", "MZN",
		    "NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK",
		    "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF", "SAR",
		    "SBD", "SCR", "SDG", "SEK", "SGD", "SHP", "SLL", "SOS", "SRD", "SSP",
		    "STN", "SVC", "SYP", "SZL", "THB", "TJS", "TMT", "TND", "TOP", "TRY",
		    "TTD", "TVD", "TWD", "TZS", "UAH", "UGX", "USD", "UYU", "UZS", "VEF",
		    "VND", "VUV", "WST", "XCD", "XOF", "YER", "ZAR", "ZMW"
		};
	JComboBox combobox1 = new JComboBox(worldCurrencies);
	JComboBox combobox2 = new JComboBox(worldCurrencies);
	String result;
	private boolean changeInProgress = false;
	
	final Button button = new Button("Change");
	
	public GUI() {
		currencyapicaller();//to combat the delay of API call
		
		ActionListener action = new Click();
		button.addActionListener(action);
		combobox1.addActionListener(action);
		combobox2.addActionListener(action);
		
		text1.setInputVerifier(new DoubleInputVerifier());
		text2.setEditable(false);
		text1.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void insertUpdate(DocumentEvent e) {
				currencyapicaller();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				currencyapicaller();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				currencyapicaller();
			};
		});
		
		frame.setLayout(null); // Use a null layout manager
		
		label.setFont(new Font("MV Boli",Font.BOLD,20)); //set font type and size
		label.setBounds(125, 10, 500, 50);
		
		combobox1.setBounds(50, 70, 100, 30);
	    text1.setBounds(50, 110, 100, 30);
	    button.setBounds(170, 90, 100, 30);
	    combobox2.setBounds(290, 70, 100, 30);
	    text2.setBounds(290, 110, 100, 30);
		
	    frame.add(label);
	    frame.add(combobox1);
	    frame.add(text1);
	    frame.add(button);
	    frame.add(combobox2);
	    frame.add(text2);
		
		frame.setVisible(true);
		frame.setSize(450, 250);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	class DoubleInputVerifier extends InputVerifier{

		public boolean verify(JComponent input) {
			JTextField textfield = (JTextField) input;
			try {
				Double.parseDouble(textfield.getText());
				return true;
			}
			catch(NumberFormatException NFE)
			{
				JOptionPane.showMessageDialog(null, "Invalid Input, Please enter 0.0 type input");
				return false;
			}
			
		}
		
	}
	public class Click implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == button) {
				if (!changeInProgress) {
                    // Set the flag to indicate that a change request is in progress
                    changeInProgress = true;

                    text1.setText(text2.getText());
                    String temp = (String) combobox1.getSelectedItem();
                    combobox1.setSelectedItem(combobox2.getSelectedItem());
                    combobox2.setSelectedItem(temp);

                    // Use SwingWorker for time-consuming task
                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() {
                            currencyapicaller();
                            return null;
                        }

                        @Override
                        protected void done() {
                            // Reset the flag when the request is complete
                            changeInProgress = false;
                        }
                    };

                    worker.execute(); // Start the SwingWorker
                }			
			}
			else if(e.getSource() == combobox1) {
				currencyapicaller();
			}
			else if(e.getSource() == combobox2) {
				currencyapicaller();
			}
		}
	}
	
	public void currencyapicaller() {
	    String amount = text1.getText();

	    if (!amount.isEmpty()) {
	        SwingWorker<Double, Void> worker = new SwingWorker<Double, Void>() {
	            @Override
	            protected Double doInBackground() throws Exception {
	                Currencymethod api = new Currencymethod(combobox1.getSelectedItem().toString(), 
	                		combobox2.getSelectedItem().toString(), text1.getText());
	                return api.currencyreturn();
	            }

	            @Override
	            protected void done() {
	                try {
	                    double result = get();
	                    if (result >= 0.00) {
	                        text2.setText(Double.toString(result));
	                    } else {
	                        text2.setText("Unknown");
	                    }
	                } catch (Exception ex) {
	                    ex.printStackTrace();
	                    text2.setText("Error");
	                }
	            }
	        };

	        worker.execute(); // Start the SwingWorker
	    } else {
	        text2.setText("0.00");
	    }
	}

	
	public String gettext() {
		return result;
	}
	
}
