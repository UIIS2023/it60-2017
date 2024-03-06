package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import shapes.Circle;


public class DlgCircle extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
    private JTextField txtX;
    private JTextField txtY;
    private JTextField txtRadius;
    
    private int xCoordinateOfCenter;
    private int yCoordinateOfCenter;
    private int radiusLength;
    
    private JLabel lblX;
    private JLabel lblY;
    private JLabel lblRadius;

    
    private Color edgeColor;
    private Color interiorColor;
    private boolean confirmed;
    private Color edgeColorOfCircle;
    private Color interiorColorOfCircle;
    private JButton btnEdgeColor;
    private JButton btnInteriorColor;
	private int drawWidth;
	private int drawHeight;

    public static void main(String[] arrayOfStrings) {
        try {
            DlgCircle dialog = new DlgCircle();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public DlgCircle() {
        setTitle("Circle");
        setBounds(100, 100, 439, 333);
        setModal(true);
        setResizable(false);

        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        GridBagLayout gbl_mainPanel = new GridBagLayout();
        gbl_mainPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_mainPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_mainPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_mainPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};

        contentPanel.setLayout(gbl_mainPanel);
        {
        	lblX = new JLabel("X coordinate of center");
        	lblX.setFont(new Font("Arial", Font.BOLD, 12));
        	
            GridBagConstraints gbc_lblXcoordinateOfCenter = new GridBagConstraints();
            gbc_lblXcoordinateOfCenter.insets = new Insets(0, 0, 5, 5);
            gbc_lblXcoordinateOfCenter.gridx = 3;
            gbc_lblXcoordinateOfCenter.gridy = 2;
            
            contentPanel.add(lblX, gbc_lblXcoordinateOfCenter);
        }
        {
        	txtX = new JTextField();
        	lblX.setLabelFor(txtX);
        	
            GridBagConstraints gbc_txtXcoordinateOfCenter = new GridBagConstraints();
            gbc_txtXcoordinateOfCenter.fill = GridBagConstraints.HORIZONTAL;
            gbc_txtXcoordinateOfCenter.insets = new Insets(0, 0, 5, 5);
            gbc_txtXcoordinateOfCenter.gridx = 7;
            gbc_txtXcoordinateOfCenter.gridy = 2;
            
            txtX.setColumns(10);
            contentPanel.add(txtX, gbc_txtXcoordinateOfCenter);
        }
        {
        	lblY = new JLabel("Y coordinate of center");
        	lblY.setFont(new Font("Arial", Font.BOLD, 12));
        	
            GridBagConstraints gbc_lblYcoordinateOfCenter = new GridBagConstraints();
            gbc_lblYcoordinateOfCenter.insets = new Insets(0, 0, 5, 5);
            gbc_lblYcoordinateOfCenter.gridx = 3;
            gbc_lblYcoordinateOfCenter.gridy = 4;
            
            contentPanel.add(lblY, gbc_lblYcoordinateOfCenter);
        }
        {
        	txtY = new JTextField();
        	lblY.setLabelFor(txtY);
        	
            GridBagConstraints gbc_txtYcoordinateOfCenter = new GridBagConstraints();
            gbc_txtYcoordinateOfCenter.fill = GridBagConstraints.HORIZONTAL;
            gbc_txtYcoordinateOfCenter.insets = new Insets(0, 0, 5, 5);
            gbc_txtYcoordinateOfCenter.gridx = 7;
            gbc_txtYcoordinateOfCenter.gridy = 4;
            
            txtY.setColumns(10);
            contentPanel.add(txtY, gbc_txtYcoordinateOfCenter);
        }
        {
        	lblRadius = new JLabel("Radius length");
        	lblRadius.setFont(new Font("Arial", Font.BOLD, 12));
        	
            GridBagConstraints gbc_lblRadiusLength = new GridBagConstraints();
            gbc_lblRadiusLength.insets = new Insets(0, 0, 5, 5);
            gbc_lblRadiusLength.gridx = 3;
            gbc_lblRadiusLength.gridy = 6;
            
            contentPanel.add(lblRadius, gbc_lblRadiusLength);
        }
        {
        	txtRadius = new JTextField();
        	lblRadius.setLabelFor(txtRadius);
        	
            GridBagConstraints gbc_txtRadiusLength = new GridBagConstraints();
            gbc_txtRadiusLength.fill = GridBagConstraints.HORIZONTAL;
            gbc_txtRadiusLength.insets = new Insets(0, 0, 5, 5);
            gbc_txtRadiusLength.gridx = 7;
            gbc_txtRadiusLength.gridy = 6;
            
            contentPanel.add(txtRadius, gbc_txtRadiusLength);
            txtRadius.setColumns(10);
        }

        btnInteriorColor = new JButton("Choose interior color");
        btnInteriorColor.setFont(new Font("Arial", Font.BOLD, 12));
        btnInteriorColor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnInteriorColor.addMouseListener(new MouseAdapter() {
        	@Override
			public void mouseClicked(MouseEvent click) {
                interiorColor = JColorChooser.showDialog(null, "Color pallete", interiorColorOfCircle);
                if (interiorColor != null) {
					interiorColorOfCircle = interiorColor;
					if (interiorColorOfCircle.equals(Color.BLACK)) btnInteriorColor.setForeground(Color.WHITE);
					else if (interiorColorOfCircle.equals(Color.WHITE)) btnInteriorColor.setForeground(Color.BLACK);
					btnInteriorColor.setBackground(interiorColorOfCircle);
				}
            }
        });

        btnEdgeColor = new JButton("Choose edge color");
        btnEdgeColor.setForeground(Color.WHITE);
        btnEdgeColor.setFont(new Font("Arial", Font.BOLD, 12));
        btnEdgeColor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnEdgeColor.addMouseListener(new MouseAdapter() {
        	@Override
			public void mouseClicked(MouseEvent click) {
                edgeColor = JColorChooser.showDialog(null, "Color pallete", edgeColorOfCircle);
                if (edgeColor != null) {
					if (edgeColor.equals(Color.WHITE)) JOptionPane.showMessageDialog(null, "Background is white");
					else {
						edgeColorOfCircle = edgeColor;
						btnEdgeColor.setBackground(edgeColorOfCircle);
					}
				}
            }
        });

        GridBagConstraints gbc_btnEdgeColor = new GridBagConstraints();
        gbc_btnEdgeColor.insets = new Insets(0, 0, 5, 5);
        gbc_btnEdgeColor.gridx = 3;
        gbc_btnEdgeColor.gridy = 8;
        contentPanel.add(btnEdgeColor, gbc_btnEdgeColor);
        btnEdgeColor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        GridBagConstraints gbc_btnInteriorColor = new GridBagConstraints();
        gbc_btnInteriorColor.anchor = GridBagConstraints.EAST;
        gbc_btnInteriorColor.insets = new Insets(0, 0, 5, 5);
        gbc_btnInteriorColor.gridx = 7;
        gbc_btnInteriorColor.gridy = 8;
        contentPanel.add(btnInteriorColor, gbc_btnInteriorColor);
        {
            JPanel buttonsPanel = new JPanel();
            buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
            {
                JButton btnConfirm = new JButton("Confirm");
                btnConfirm.setFont(new Font("Arial", Font.BOLD, 12));
                btnConfirm.setBackground(Color.GREEN);
                btnConfirm.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btnConfirm.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent click) {
                        if (txtX.getText().isEmpty() || txtY.getText().isEmpty() || txtRadius.getText().isEmpty())
                            JOptionPane.showMessageDialog(getParent(), "Values cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                        else {
                            try {
                            	xCoordinateOfCenter = Integer.parseInt(txtX.getText());
                            	yCoordinateOfCenter = Integer.parseInt(txtY.getText());
                            	radiusLength = Integer.parseInt(txtRadius.getText());
                                if (xCoordinateOfCenter <= 0 || yCoordinateOfCenter <= 0 || radiusLength <= 0) JOptionPane.showMessageDialog(getParent(), "X and Y coordinates of center and radius length of circle must be positive numbers!", "Error", JOptionPane.ERROR_MESSAGE);
                                else if (radiusLength + xCoordinateOfCenter > drawWidth || radiusLength + yCoordinateOfCenter > drawHeight || yCoordinateOfCenter - radiusLength <= 0 || xCoordinateOfCenter - radiusLength < 0) JOptionPane.showMessageDialog(null, "The circle goes out of drawing!");
                        		else {
                        			confirmed = true;
                        			setVisible(false);
                        			dispose();          
                                }
                            } catch (NumberFormatException nfe) {
                                JOptionPane.showMessageDialog(getParent(), "X and Y coordinates of center and radius length of circle must be whole numbers!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                });

                btnConfirm.setActionCommand("OK");
                buttonsPanel.add(btnConfirm);
                getRootPane().setDefaultButton(btnConfirm);
            }
            {
                JButton btnCancel = new JButton("Cancel");
                btnCancel.setFont(new Font("Arial", Font.BOLD, 12));
                btnCancel.setBackground(Color.RED);
                btnCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btnCancel.addMouseListener(new MouseAdapter() {
                	@Override
        			public void mouseClicked(MouseEvent click) {
                		setVisible(false);
                        dispose();
                    }
                });

                btnCancel.setActionCommand("Cancel");
                buttonsPanel.add(btnCancel);
            }
        }
    }

 
    public void write(int xOfClick, int yOfClick, int drawWidth, int drawHeight) {
    	txtX.setText(String.valueOf(xOfClick));
    	txtX.setEnabled(false);
    	txtY.setText(String.valueOf(yOfClick));
    	txtY.setEnabled(false);
        this.drawWidth = drawWidth;
		this.drawHeight = drawHeight;
    }


    public void deleteButtons() {
        btnEdgeColor.setVisible(false);
        btnInteriorColor.setVisible(false);
    }


    public void fillUp(Circle circle, int drawWidth, int drawHeight) {
    	txtX.setText(String.valueOf(circle.getCenter().getXcoordinate()));
    	txtY.setText(String.valueOf(circle.getCenter().getYcoordinate()));
    	txtRadius.setText(String.valueOf(circle.getRadius()));
        edgeColorOfCircle = circle.getColor();
        interiorColorOfCircle = circle.getInteriorColor();
        if (interiorColorOfCircle.equals(Color.BLACK)) btnInteriorColor.setForeground(Color.WHITE);
		else if (interiorColorOfCircle.equals(Color.WHITE)) btnInteriorColor.setForeground(Color.BLACK);
		btnEdgeColor.setBackground(edgeColorOfCircle);
		btnInteriorColor.setBackground(interiorColorOfCircle);
        this.drawWidth = drawWidth;
		this.drawHeight = drawHeight;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
    
    public int getXcoordinateOfCenter() {
        return xCoordinateOfCenter;
    }

    public int getYcoordinateOfCenter() {
        return yCoordinateOfCenter;
    }

    public int getRadiusLength() {
        return radiusLength;
    }

    public Color getEdgeColor() {
        return edgeColorOfCircle;
    }

    public Color getInteriorColor() {
    	return interiorColorOfCircle;
    }
}