package drawing;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import shapes.Rectangle;


public class DlgRectangle extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtWidth;
	private JTextField txtHeight;
	
	private JLabel lblX;
	private JLabel lblY;
	private JLabel lblWidth;
	private JLabel lblHeight;
	
	private int xCoordinate;
	private int yCoordinate;
	private int width;
	private int height;
	
	private Color edgeColor;
	private Color interiorColor;
	private Color edgeColorOfRectangle;
	private Color interiorColorOfRectangle;
	private boolean confirmed;
	private JButton btnEdgeColor;
	private JButton btnInteriorColor;
	private int drawWidth;
	private int drawHeight;

	public static void main(String [] arrayOfStrings) {
		try {
			DlgRectangle dialog = new DlgRectangle();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public DlgRectangle() {
		setModal(true);
		setResizable(false);
		setTitle("Rectangle");
		setBounds(100, 100, 418, 386);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_mainPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_mainPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_mainPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_mainPanel);
		{
			lblX = new JLabel("X coordinate");
			GridBagConstraints gbc_lblXcoordinate = new GridBagConstraints();
			gbc_lblXcoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_lblXcoordinate.gridx = 3;
			gbc_lblXcoordinate.gridy = 2;
			contentPanel.add(lblX, gbc_lblXcoordinate);
		}
		{
			txtX = new JTextField();
			lblX.setLabelFor(txtX);
			GridBagConstraints gbc_txtXcoordinate = new GridBagConstraints();
			gbc_txtXcoordinate.anchor = GridBagConstraints.NORTH;
			gbc_txtXcoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_txtXcoordinate.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtXcoordinate.gridx = 6;
			gbc_txtXcoordinate.gridy = 2;
			contentPanel.add(txtX, gbc_txtXcoordinate);
			txtX.setColumns(10);
		}
		{
			lblY = new JLabel("Y coordinate");
			GridBagConstraints gbc_lblYcoordinate = new GridBagConstraints();
			gbc_lblYcoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_lblYcoordinate.gridx = 3;
			gbc_lblYcoordinate.gridy = 4;
			contentPanel.add(lblY, gbc_lblYcoordinate);
		}
		{
			txtY = new JTextField();
			lblY.setLabelFor(txtY);
			GridBagConstraints gbc_txtYcoordinate = new GridBagConstraints();
			gbc_txtYcoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_txtYcoordinate.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtYcoordinate.gridx = 6;
			gbc_txtYcoordinate.gridy = 4;
			contentPanel.add(txtY, gbc_txtYcoordinate);
			txtY.setColumns(10);
		}
		{
			lblWidth = new JLabel("width");
			GridBagConstraints gbc_lblWidth = new GridBagConstraints();
			gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
			gbc_lblWidth.gridx = 3;
			gbc_lblWidth.gridy = 6;
			contentPanel.add(lblWidth, gbc_lblWidth);
		}
		{
			txtWidth = new JTextField();
			lblWidth.setLabelFor(txtWidth);
			GridBagConstraints gbc_txtWidth = new GridBagConstraints();
			gbc_txtWidth.insets = new Insets(0, 0, 5, 5);
			gbc_txtWidth.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtWidth.gridx = 6;
			gbc_txtWidth.gridy = 6;
			contentPanel.add(txtWidth, gbc_txtWidth);
			txtWidth.setColumns(10);
		}
		{
			lblHeight = new JLabel("height");
			GridBagConstraints gbc_lblHeight = new GridBagConstraints();
			gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
			gbc_lblHeight.gridx = 3;
			gbc_lblHeight.gridy = 8;
			contentPanel.add(lblHeight, gbc_lblHeight);
		}
		{
			txtHeight = new JTextField();
			lblHeight.setLabelFor(txtHeight);
			GridBagConstraints gbc_txtHeight = new GridBagConstraints();
			gbc_txtHeight.insets = new Insets(0, 0, 5, 5);
			gbc_txtHeight.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtHeight.gridx = 6;
			gbc_txtHeight.gridy = 8;
			contentPanel.add(txtHeight, gbc_txtHeight);
			txtHeight.setColumns(10);
		}
		{
			btnInteriorColor = new JButton("Choose interior color");
			btnInteriorColor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnInteriorColor.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent click) {
					interiorColor = JColorChooser.showDialog(null, "Colors pallete", interiorColorOfRectangle);
					if (interiorColor != null) {
						interiorColorOfRectangle= interiorColor;
						if (interiorColorOfRectangle.equals(Color.BLACK)) btnInteriorColor.setForeground(Color.WHITE);
						else if (interiorColorOfRectangle.equals(Color.WHITE)) btnInteriorColor.setForeground(Color.BLACK);
						btnInteriorColor.setBackground(interiorColorOfRectangle);
					}
				}
			});
			
			btnEdgeColor = new JButton("Choose edge color");
			btnEdgeColor.setForeground(Color.WHITE);
			btnEdgeColor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnEdgeColor.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent click) {
					edgeColor = JColorChooser.showDialog(null, "Colors pallete", edgeColorOfRectangle);
					if (edgeColor != null) {
						if (edgeColor.equals(Color.WHITE)) JOptionPane.showMessageDialog(null, "Background is white");
						else {
							edgeColorOfRectangle = edgeColor;
							btnEdgeColor.setBackground(edgeColorOfRectangle);
						}
					}
				}
			});
			
			GridBagConstraints gbc_btnEdgeColor = new GridBagConstraints();
			gbc_btnEdgeColor.insets = new Insets(0, 0, 5, 5);
			gbc_btnEdgeColor.gridx = 3;
			gbc_btnEdgeColor.gridy = 10;
			contentPanel.add(btnEdgeColor, gbc_btnEdgeColor);
			GridBagConstraints gbc_btnInteriorColor = new GridBagConstraints();
			gbc_btnInteriorColor.insets = new Insets(0, 0, 5, 5);
			gbc_btnInteriorColor.gridx = 6;
			gbc_btnInteriorColor.gridy = 10;
			contentPanel.add(btnInteriorColor, gbc_btnInteriorColor);
		}
		{
			JPanel buttonsPanel = new JPanel();
			buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
			{
				JButton btnConfirm = new JButton("Confirm");
				btnConfirm.setBackground(Color.GREEN);
				btnConfirm.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btnConfirm.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent click) {
						if (txtX.getText().isEmpty() || txtY.getText().isEmpty() || txtWidth.getText().isEmpty() || txtHeight.getText().isEmpty())
							JOptionPane.showMessageDialog(getParent(), "Values cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
						else {
							try {	
								xCoordinate = Integer.parseInt(txtX.getText());
								yCoordinate = Integer.parseInt(txtY.getText());
								width = Integer.parseInt(txtWidth.getText());
								height = Integer.parseInt(txtHeight.getText());
								if(xCoordinate <= 0 || yCoordinate <= 0 || width <= 0 || height <= 0) JOptionPane.showMessageDialog(getParent(), "X and Y coordinates of up left point, width and height of rectangle must be positive numbers!", "Error", JOptionPane.ERROR_MESSAGE);
								else if (width + xCoordinate > drawWidth || height + yCoordinate > drawHeight) JOptionPane.showMessageDialog(null, "The rectangle goes out of drawing!");
								else {
									confirmed = true;
									setVisible(false);
									dispose();
								}
							} catch (NumberFormatException nfe) {
								JOptionPane.showMessageDialog(getParent(),"X and Y coordinates of up left point, width and height of rectangle must be whole numbers!", "Error", JOptionPane.ERROR_MESSAGE);
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


	public void write(int xClick, int yClick, int drawWidth, int drawHeight) {
		txtX.setText(String.valueOf(xClick));
		txtX.setEnabled(false);
		txtY.setText(String.valueOf(yClick));
		txtY.setEnabled(false);
		this.drawWidth = drawWidth;
		this.drawHeight = drawHeight;
	}


	public void fillUp(Rectangle rectangle, int drawWidth, int drawHeight) {
		txtX.setText(String.valueOf((rectangle.getUpLeft().getXcoordinate())));
		txtY.setText(String.valueOf((rectangle.getUpLeft().getYcoordinate())));
		txtWidth.setText(String.valueOf(rectangle.getWidth()));
		txtHeight.setText(String.valueOf(rectangle.getHeight()));
		edgeColorOfRectangle = rectangle.getColor();
		interiorColorOfRectangle = rectangle.getInteriorColor();
		if (interiorColorOfRectangle.equals(Color.BLACK)) btnInteriorColor.setForeground(Color.WHITE);
		else if (interiorColorOfRectangle.equals(Color.WHITE)) btnInteriorColor.setForeground(Color.BLACK);
		btnEdgeColor.setBackground(edgeColorOfRectangle);
		btnInteriorColor.setBackground(interiorColorOfRectangle);
		this.drawWidth = drawWidth;
		this.drawHeight = drawHeight;
	}


	public void deleteButtons() {
		btnEdgeColor.setVisible(false);
		btnInteriorColor.setVisible(false);
	}
	
	public boolean isConfirmed() {
		return confirmed;
	}

	public int getXcoordinate() {
		return xCoordinate;
	}

	public int getYcoordinate() {
		return yCoordinate;
	}

	public int getRectangleWidth() {
		return width;
	}

	public int getRectangleHeight() {
		return height;
	}

	public Color getEdgeColor() {
		return edgeColorOfRectangle;
	}

	public Color getInteriorColor() {
		return interiorColorOfRectangle;
	}
}