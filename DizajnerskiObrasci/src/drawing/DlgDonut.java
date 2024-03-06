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
import shapes.Donut;


public class DlgDonut extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
    private JTextField txtX;
    private JTextField txtY;
    
    private JTextField txtRadius;
    private JTextField txtInnerRadius;
    
    private JLabel lblX;
    private JLabel lblY;
    private JLabel lblRadius;
    private JLabel lblInnerRadius;

    
    private int xCoordinateOfCenter;
    private int yCoordinateOfCenter;
    private int radiusLength;
    private int innerRadiusLength;
    
    private Color edgeColor;
    private Color interiorColor;
    private boolean confirmed;
    private Color edgeColorOfDonut;
    private Color interiorColorOfDonut;
    private JButton btnEdgeColor;
    private JButton btnInteriorColor;
	private int drawWidth;
	private int drawHeight;

    public static void main(String[] arrayOfStrings) {
        try {
            DlgDonut dialog = new DlgDonut();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public DlgDonut() {
        setModal(true);
        setResizable(false);
        setTitle("Donut");
        setBounds(100, 100, 449, 342);
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
        
        {
        	lblInnerRadius = new JLabel("Inner radius length");
        	lblInnerRadius.setFont(new Font("Arial", Font.BOLD, 12));
            GridBagConstraints gbc_lblInnerRadiusLength = new GridBagConstraints();
            gbc_lblInnerRadiusLength.insets = new Insets(0, 0, 5, 5);
            gbc_lblInnerRadiusLength.gridx = 3;
            gbc_lblInnerRadiusLength.gridy = 8;
            contentPanel.add(lblInnerRadius, gbc_lblInnerRadiusLength);
        }
        {
        	txtInnerRadius = new JTextField();
        	lblInnerRadius.setLabelFor(txtInnerRadius);
            GridBagConstraints gbc_txtInnerRadiusLength = new GridBagConstraints();
            gbc_txtInnerRadiusLength.fill = GridBagConstraints.HORIZONTAL;
            gbc_txtInnerRadiusLength.insets = new Insets(0, 0, 5, 5);
            gbc_txtInnerRadiusLength.gridx = 7;
            gbc_txtInnerRadiusLength.gridy = 8;
            contentPanel.add(txtInnerRadius, gbc_txtInnerRadiusLength);
            txtInnerRadius.setColumns(10);
        }

        btnInteriorColor = new JButton("Choose interior color");
        btnInteriorColor.setFont(new Font("Arial", Font.BOLD, 12));
        btnInteriorColor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnInteriorColor.addMouseListener(new MouseAdapter() {
        	@Override
			public void mouseClicked(MouseEvent click) {
                interiorColor = JColorChooser.showDialog(null, "Color pallete", interiorColorOfDonut);
                if (interiorColor != null) {
                	interiorColorOfDonut = interiorColor;
					if (interiorColorOfDonut.equals(Color.BLACK)) btnInteriorColor.setForeground(Color.WHITE);
					else if (interiorColorOfDonut.equals(Color.WHITE)) btnInteriorColor.setForeground(Color.BLACK);
					btnInteriorColor.setBackground(interiorColorOfDonut);
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
                edgeColor = JColorChooser.showDialog(null, "Color pallete", edgeColorOfDonut);
                if (edgeColor != null) {
					if (edgeColor.equals(Color.WHITE)) JOptionPane.showMessageDialog(null, "Background is white");
					else {
						edgeColorOfDonut = edgeColor;
						btnEdgeColor.setBackground(edgeColorOfDonut);
					}
				}
            }
        });

        GridBagConstraints gbc_btnEdgeColor = new GridBagConstraints();
        gbc_btnEdgeColor.insets = new Insets(0, 0, 5, 5);
        gbc_btnEdgeColor.gridx = 3;
        gbc_btnEdgeColor.gridy = 10;
        contentPanel.add(btnEdgeColor, gbc_btnEdgeColor);
        btnEdgeColor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        GridBagConstraints gbc_btnInteriorColor = new GridBagConstraints();
        gbc_btnInteriorColor.anchor = GridBagConstraints.EAST;
        gbc_btnInteriorColor.insets = new Insets(0, 0, 5, 5);
        gbc_btnInteriorColor.gridx = 7;
        gbc_btnInteriorColor.gridy = 10;
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
                        if (txtX.getText().isEmpty() || txtY.getText().isEmpty() || txtRadius.getText().isEmpty()|| txtInnerRadius.getText().isEmpty())
                            JOptionPane.showMessageDialog(getParent(), "Values cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                        else {
                            try {
                            	xCoordinateOfCenter = Integer.parseInt(txtX.getText());
                            	yCoordinateOfCenter = Integer.parseInt(txtY.getText());
                            	radiusLength = Integer.parseInt(txtRadius.getText());
                            	innerRadiusLength=Integer.parseInt(txtInnerRadius.getText());
                                if (xCoordinateOfCenter <= 0 || yCoordinateOfCenter <= 0 || radiusLength <= 0 || innerRadiusLength<=0) JOptionPane.showMessageDialog(getParent(), "X and Y coordinates of center and radius length and inner radius length of donut must be positive numbers!", "Error", JOptionPane.ERROR_MESSAGE);   
                                else if (radiusLength + xCoordinateOfCenter > drawWidth || radiusLength + yCoordinateOfCenter > drawHeight || yCoordinateOfCenter - radiusLength <= 0 || xCoordinateOfCenter - radiusLength < 0) JOptionPane.showMessageDialog(null, "The donut goes out of drawing!");
                        		else if (innerRadiusLength>radiusLength) JOptionPane.showMessageDialog(null, "Radius must be greater than inner radius!");
                                else {
                        			confirmed = true;
                        			setVisible(false);
                        			dispose();          
                                }
                            } catch (NumberFormatException nfe) {
                                JOptionPane.showMessageDialog(getParent(), "X and Y coordinates of center and radius length and inner radius length of donut must be whole numbers!", "Error", JOptionPane.ERROR_MESSAGE);
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


    public void fillUp(Donut donut, int drawWidth, int drawHeight) {
    	txtX.setText(String.valueOf(donut.getCenter().getXcoordinate()));
    	txtY.setText(String.valueOf(donut.getCenter().getYcoordinate()));
    	txtRadius.setText(String.valueOf(donut.getRadius()));
    	txtInnerRadius.setText(String.valueOf(donut.getInnerRadius()));
        edgeColorOfDonut = donut.getColor();
        interiorColorOfDonut = donut.getInteriorColor();
        if (interiorColorOfDonut.equals(Color.BLACK)) btnInteriorColor.setForeground(Color.WHITE);
		else if (interiorColorOfDonut.equals(Color.WHITE)) btnInteriorColor.setForeground(Color.BLACK);
		btnEdgeColor.setBackground(edgeColorOfDonut);
		btnInteriorColor.setBackground(interiorColorOfDonut);
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

    public int getInnerRadiusLength() {
        return innerRadiusLength;
    }
    
    public Color getEdgeColor() {
        return edgeColorOfDonut;
    }

    public Color getInteriorColor() {
    	return interiorColorOfDonut;
    }
}