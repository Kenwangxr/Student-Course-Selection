package com.student.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import com.student.AppConstants;
import com.student.base.BaseDAO.CourseNotFoundException;
import com.student.base.BaseDAO.CourseNotSelectedException;
import com.student.dao.StudentDAO;
import com.student.model.Student;

/**
 * @Description: Student Select Course View
 * @ClassName: StudentView
 * 
 */
public class StudentView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Student student;
    private JTable infotable, coursetable, scoretable, selectedtable;
    private static final String[] infocolumn = {AppConstants.SNO, AppConstants.SNAME,
            AppConstants.SEX, AppConstants.AGE, AppConstants.SDEPT};
    private static final String[] coursecolumn = {AppConstants.CNO, AppConstants.CNAME,
            AppConstants.CREDIT, AppConstants.CDEPT, AppConstants.TNAME};
    private static final String[] scorecolumn =
            {AppConstants.CNO, AppConstants.CNAME, AppConstants.SCORE};
    private JTextField textField;


    public StudentView(Student student) {
        this.student = student;
        System.out.println("Student " + student.getSno() + " Login Success.");

        setResizable(false);
        setTitle(AppConstants.STUDENT_TITLE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("Student " + student.getSno() + " Logout.");
                new LoginView();
            }
        });

        setVisible(true);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JPanel btnpanel = new JPanel();
        contentPane.add(btnpanel, BorderLayout.EAST);
        btnpanel.setLayout(new BoxLayout(btnpanel, BoxLayout.Y_AXIS));

        JButton selectbtn = new JButton(AppConstants.STUDENT_SELECT);
        selectbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton dropbtn = new JButton(AppConstants.STUDENT_DROP);
        dropbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton closebtn = new JButton(AppConstants.STUDENT_CLOSE);
        closebtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        selectbtn.addActionListener(new SelectListener());
        dropbtn.addActionListener(new DropListener());
        closebtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnpanel.add(Box.createRigidArea(new Dimension(100, 50)));
        btnpanel.add(selectbtn);
        btnpanel.add(Box.createRigidArea(new Dimension(100, 50)));
        btnpanel.add(dropbtn);
        btnpanel.add(Box.createRigidArea(new Dimension(100, 50)));
        btnpanel.add(closebtn);

        JPanel centerpanel = new JPanel();
        contentPane.add(centerpanel, BorderLayout.CENTER);
        centerpanel.setLayout(new GridLayout(2, 2, 15, 15));

        initInfo(centerpanel);
        initCourse(centerpanel);
        initScore(centerpanel);
        initSelect(centerpanel);
        getRootPane().setDefaultButton(selectbtn);
    }

    private void initInfo(JPanel centerpanel) {
        System.err.println("Loading Student Info...");
        JPanel panel = new JPanel();
        centerpanel.add(panel);
        panel.setLayout(new BorderLayout());

        JPanel label = new JPanel();
        panel.add(label, BorderLayout.NORTH);
        label.add(new JLabel(AppConstants.STUDENT_INFO));

        infotable = new JTable();
        infotable.setEnabled(false);
        String[][] result = StudentDAO.getInstance().queryStudent(student.getSno());

        // Assign the information.
        student.setSname(result[0][1]);
        student.setSex(result[0][2]);
        try {
            // Maybe the age is NULL
            student.setAge(Integer.parseInt(result[0][3]));
        } catch (NumberFormatException e) {
            student.setAge(-1);
        }
        student.setSdept(result[0][4]);
        student.setUsername(result[0][5]);

        initTable(infotable, result, infocolumn);
        JScrollPane scrollpane = new JScrollPane(infotable);
        infotable.getTableHeader().setReorderingAllowed(false);
        panel.add(scrollpane, BorderLayout.CENTER);
    }

    private void initCourse(JPanel centerpanel) {
        System.err.println("Loading Course Info...");
        JPanel panel = new JPanel();
        centerpanel.add(panel);
        panel.setLayout(new BorderLayout());

        JPanel mainpanel = new JPanel();
        panel.add(mainpanel, BorderLayout.CENTER);
        mainpanel.setLayout(new BorderLayout());

        JPanel label = new JPanel();
        mainpanel.add(label, BorderLayout.NORTH);

        JLabel courselabel = new JLabel(AppConstants.STUDENT_COURSE);
        label.add(courselabel);

        coursetable = new JTable();
        coursetable.setEnabled(false);

        String[][] result = StudentDAO.getInstance().queryCourses(String.valueOf(student.getSno()));

        initTable(coursetable, result, coursecolumn);
        JScrollPane scrollPane = new JScrollPane(coursetable);
        coursetable.getTableHeader().setReorderingAllowed(false);
        mainpanel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputpanel = new JPanel();
        panel.add(inputpanel, BorderLayout.SOUTH);

        inputpanel.add(new JLabel(AppConstants.STUDENT_INPUT));
        textField = new JTextField();
        inputpanel.add(textField);
        textField.setColumns(10);
    }


    private void initScore(JPanel centerpanel) {
        System.err.println("Loading Score Info...");
        JPanel panel = new JPanel();
        centerpanel.add(panel);
        panel.setLayout(new BorderLayout());

        JPanel label = new JPanel();
        panel.add(label, BorderLayout.NORTH);
        label.add(new JLabel(AppConstants.STUDENT_SCORE));

        scoretable = new JTable();
        scoretable.setEnabled(false);
        String[][] result = StudentDAO.getInstance().queryStuGrade(String.valueOf(student.getSno()));

        initTable(scoretable, result, scorecolumn);
        JScrollPane scrollpane = new JScrollPane(scoretable);
        scoretable.getTableHeader().setReorderingAllowed(false);
        panel.add(scrollpane);

    }

    private void initSelect(JPanel centerpanel) {
        System.err.println("Loading Selected Info...");
        JPanel panel = new JPanel();
        centerpanel.add(panel);
        panel.setLayout(new BorderLayout());

        JPanel label = new JPanel();
        panel.add(label, BorderLayout.NORTH);
        label.add(new JLabel(AppConstants.STUDENT_SELECTED));

        selectedtable = new JTable();
        selectedtable.setEnabled(false);
        String[][] result = StudentDAO.getInstance().querySelectedCourse(String.valueOf(student.getSno()));

        initTable(selectedtable, result, coursecolumn);
        JScrollPane scrollpane = new JScrollPane(selectedtable);
        selectedtable.getTableHeader().setReorderingAllowed(false);
        panel.add(scrollpane);
    }

    private void initTable(JTable jTable, String[][] result, String[] column) {
        jTable.setModel(new DefaultTableModel(result, column));
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    private class SelectListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String cno = textField.getText();
            if (cno.equals("")) {
                JOptionPane.showMessageDialog(null, AppConstants.CNO_NULL_ERROR);
                return;
            }
            try {
                StudentDAO.getInstance().queryCourseGrade(String.valueOf(student.getSno()), cno);
                JOptionPane.showMessageDialog(null, AppConstants.CNO_SELECTED_ERROR,
                        AppConstants.ERROR, JOptionPane.ERROR_MESSAGE);
            } catch (CourseNotFoundException e1) {
                JOptionPane.showMessageDialog(null, AppConstants.CNO_NOT_EXIST_ERROR,
                        AppConstants.ERROR, JOptionPane.ERROR_MESSAGE);
            } catch (CourseNotSelectedException e2) {
                StudentDAO.getInstance().selectCourse(String.valueOf(student.getSno()), cno);
                textField.setText(null);
                updateTables();
                System.out.println("Student " + student.getSno() + " selected course " + cno + ".");
            } catch (NumberFormatException e3) {
                JOptionPane.showMessageDialog(null, AppConstants.CNO_SELECTED_ERROR,
                        AppConstants.ERROR, JOptionPane.ERROR_MESSAGE);
            } catch (Exception e4) {
                System.err.println("Unknown Error!");
            }
        }

    }

    private class DropListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String cno = textField.getText();
            if (cno.equals("")) {
                JOptionPane.showMessageDialog(null, AppConstants.CNO_NULL_ERROR);
                return;
            }
            try {
                StudentDAO.getInstance().queryCourseGrade(String.valueOf(student.getSno()), cno);
                JOptionPane.showMessageDialog(null, AppConstants.CNO_GRADED_ERROR,
                        AppConstants.ERROR, JOptionPane.ERROR_MESSAGE);
            } catch (CourseNotFoundException e2) {
                JOptionPane.showMessageDialog(null, AppConstants.CNO_NOT_EXIST_ERROR,
                        AppConstants.ERROR, JOptionPane.ERROR_MESSAGE);
            } catch (CourseNotSelectedException e2) {
                JOptionPane.showMessageDialog(null, AppConstants.CNO_NOT_SELECTED_ERROR,
                        AppConstants.ERROR, JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e2) {
                StudentDAO.getInstance().dropCourse(String.valueOf(student.getSno()), cno);
                textField.setText(null);
                updateTables();
                System.out.println("Student " + student.getSno() + " droped course " + cno + ".");
            } catch (Exception e2) {
                System.err.println("Unknown Error!");
            }
        }
    }

    private void updateTables() {
        initTable(coursetable, StudentDAO.getInstance().queryCourses(String.valueOf(student.getSno())),
                coursecolumn);
        initTable(selectedtable, StudentDAO.getInstance().querySelectedCourse(String.valueOf(student.getSno())),
                coursecolumn);
    }
}
