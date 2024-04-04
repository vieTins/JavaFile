package Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.text.View;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import Model.Document;
import View.TextEditer;

public class Controller {
   private Document model ;
   private TextEditer view;
public Controller(Document model, TextEditer view) {
	super();
	this.model = model;
	this.view = view;
	 view.addTreeSelectionListener(new TreeSelectionListener() {
         public void valueChanged(TreeSelectionEvent e) {
             DefaultMutableTreeNode node = view.getLastSelectedPathComponent();
             if (node == null) return;
             File file = (File) node.getUserObject();
             if (file != null && file.isFile()) {
                 try {
                     String content = new String(Files.readAllBytes(file.toPath()));
                     view.setTextArea(content);
                 } catch (IOException ex) {
                     ex.printStackTrace();
                 }
             }
         }
     });
	 view.addDocumentListener(new DocumentListener() {
         @Override
         public void insertUpdate(DocumentEvent e) {
             updateModel();
         }

         @Override
         public void removeUpdate(DocumentEvent e) {
             updateModel();
         }

         @Override
         public void changedUpdate(DocumentEvent e) {
             updateModel();
         }

         private void updateModel() {
//        	 asList -> chuyển một mảng thành một danh sách 
             model.setLines(Arrays.asList(view.getTextArea().split("\\n")));
         }
     });
	 
	 
}


public Controller() {
	super();
}
public void deleteFile(File file) {
	model = new Document() ;
	view = new TextEditer() ;
	boolean deleted = model.deleteFile(file) ;
	if (deleted) {
//		 Nếu xóa thành công , cập nhật giao diện ngươi dùng tương ứng 
		DefaultMutableTreeNode selectedNode = view.getLastSelectedPathComponent();
		if (selectedNode!= null) {
			DefaultTreeModel treeModel = (DefaultTreeModel) view.getTree().getModel().getRoot() ;
			treeModel.removeNodeFromParent(selectedNode);
		}	
	}
	else {
		JOptionPane.showMessageDialog(null, "Failed to delete File");
	}
}

}
