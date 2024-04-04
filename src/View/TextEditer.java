package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JTree;
import javax.swing.JTextArea;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import Controller.Controller;
import Model.Document;

import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TextEditer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTree tree;
	private JTextArea textArea;
	private Controller controller;
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {				
					TextEditer frame = new TextEditer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TextEditer() {
		controller = new Controller();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 548);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(0, 0, 207, 511);
		contentPane.add(panel);
		panel.setLayout(null);
	
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        tree = new JTree(root);
		tree.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tree.setBounds(1, 1, 206, 508);
		panel.add(tree);
		
		JScrollPane scrollPane = new JScrollPane(tree);
		scrollPane.setBounds(0, 0, 207, 508);
		panel.add(scrollPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_1.setBounds(217, 0, 569, 443);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		

		
		textArea = new JTextArea();
		textArea.setFont(new Font("Sylfaen", Font.PLAIN, 17));
		textArea.setBounds(10, 10, 549, 413);
		panel_1.add(textArea);
		
		JScrollPane scrollPane_1 = new JScrollPane(textArea);
		scrollPane_1.setBounds(10, 10, 549, 423);
		panel_1.add(scrollPane_1);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 saveSelectedFile(); 
			}
		});
		btnNewButton.setBounds(672, 453, 104, 29);
		contentPane.add(btnNewButton);
		
		JButton btnDeleteFile = new JButton("Delete File");
		btnDeleteFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deleteSelectedFile();
			}
		});
		btnDeleteFile.setBounds(558, 453, 104, 29);
		contentPane.add(btnDeleteFile);
		
		JButton btnCreateNew = new JButton("Create New");
		btnCreateNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createFile();
			}
		});
		btnCreateNew.setBounds(444, 453, 104, 29);
		contentPane.add(btnCreateNew);
		
		JButton btnReload = new JButton("Reload");
		btnReload.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				reloadTree();
			}
		});
		btnReload.setBounds(330, 453, 104, 29);
		contentPane.add(btnReload);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				editFileName(); 
			}
		});
		btnEdit.setBounds(217, 453, 104, 29);
		contentPane.add(btnEdit);
		createNodes(root, new File("D:\\"));
	}
    private void createNodes(DefaultMutableTreeNode root, File file) {
    	DefaultMutableTreeNode node = new DefaultMutableTreeNode(file);
        root.add(node);
        if (file.isDirectory() && file.canRead()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    createNodes(node, f);
                }
            }
        }
    }
    public DefaultMutableTreeNode getLastSelectedPathComponent() {
		return (DefaultMutableTreeNode) tree.getLastSelectedPathComponent() ;
	}

	public String getTextArea() {
		return textArea.getText() ;
	}
    
	public JTree getTree() {
		return tree;
	}

	public void setTree(JTree tree) {
		this.tree = tree;
	}

	public void setTextArea(String text) {
		this.textArea.setText(text);
	}
//    thêm một  DocumentListener vào Document của JTextArea
//	 sử lý sự kiện khi textarea thay đổi 
	public void addDocumentListener(DocumentListener listener) {
		textArea.getDocument().addDocumentListener(listener);
/*
 *  gọi phương thức addDocumentListener(listener) trên đối tượng Document, truyền vào DocumentListener đã được chỉ định.
 *  Điều này sẽ kích hoạt các phương thức của DocumentListener (insertUpdate, removeUpdate, changedUpdate) mỗi khi nội dung của JTextArea thay đổi.	
 */
	}
	 public void addTreeSelectionListener(TreeSelectionListener listener) {
	        tree.addTreeSelectionListener(listener);
	 }
	 private void saveSelectedFile() {
		    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		    if (selectedNode != null) {
		        File selectedFile = (File) selectedNode.getUserObject();
		        if (selectedFile.isFile()) {
		            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
		                writer.write(textArea.getText());
		                JOptionPane.showMessageDialog(null, "Lưu thành công");
		            } catch (IOException ex) {
		                ex.printStackTrace();
		            }
		        }
		    }
	 }
	 public void createFile() {
			JFileChooser jFileChooser = new JFileChooser() ;
			jFileChooser.setDialogTitle("Save as");
			int userSelection = jFileChooser.showSaveDialog(this) ;
//			nếu người dùng chọn một đường dẫn 
			if (userSelection == JFileChooser.APPROVE_OPTION) {
				File newFile = jFileChooser.getSelectedFile() ;
//				Kiểm tra xem tệp đã tồn tai hay chưa
				if (!newFile.exists()) {
					try {
						if (newFile.createNewFile()) {
							JOptionPane.showMessageDialog(null, "File created : " + newFile.getName());
						}
						else {
							JOptionPane.showMessageDialog(null , "File already exits");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					DefaultMutableTreeNode root	= (DefaultMutableTreeNode) this.getTree().getModel().getRoot();
		            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newFile) ;
		            root.add(newNode); 
		            DefaultTreeModel treeModel = (DefaultTreeModel) this.getTree().getModel();
		            treeModel.reload();
				}
				else {
					JOptionPane.showMessageDialog(null , "File already exits");
				}
			}
		}
	 private void deleteSelectedFile () {
		 DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent() ;
		 if (selectedNode!= null) {
			 File selectedFile = (File) selectedNode.getUserObject() ; 
			 if (selectedFile.isFile()) {
				int result =  JOptionPane.showConfirmDialog(this, "Are you sure you want to delete file " + selectedFile.getName() , "ConFirm Delete " , JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					controller.deleteFile(selectedFile);
					JOptionPane.showMessageDialog(this, "Deleted File");
				}
			 }
		 }
	 }
	 private void reloadTree() {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot() ;
		root.removeAllChildren();
		createNodes(root, new File("D:\\"));
		((DefaultTreeModel) tree.getModel()).reload() ;
		textArea.setText("");
		JOptionPane.showMessageDialog(this, "Reload...");
	}
	 private void editFileName() {
		 DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent() ;
		  if (selectedNode != null) {
			  File selectedFile = (File) selectedNode.getUserObject() ;
			  if (selectedFile.isFile()) {
				  String newFileName = JOptionPane.showInputDialog(this, "Enter New Name") ;
				  if (newFileName!= null && !newFileName.isEmpty()) {
					  File newFile = new File(selectedFile.getParentFile() , newFileName) ;
					  if (selectedFile.renameTo(newFile)) {
						  selectedNode.setUserObject(newFile);
						  ((DefaultTreeModel) tree.getModel()).nodeChanged(selectedNode) ;
					  }
					  else {
						  JOptionPane.showMessageDialog(this, "Failed to rename File");
					  }
				  }
			  }
		  }
	 }
}
