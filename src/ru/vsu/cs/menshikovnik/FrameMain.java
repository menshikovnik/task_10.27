package ru.vsu.cs.menshikovnik;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import ru.vsu.cs.util.ArrayUtils;
import ru.vsu.cs.util.JTableUtils;
import ru.vsu.cs.util.SwingUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal", "ConstantConditions", "Convert2Lambda"})
public class FrameMain extends JFrame {
    private JPanel panelMain;
    private JTable tableInput;
    private JButton buttonLoadInputFromFile;
    private JButton buttonRandomInput;
    private JButton buttonSaveInputInfoFile;
    private JButton buttonOperation;
    private JButton buttonSaveOutputIntoFile;
    private JTable tableOutput;
    private JFileChooser fileChooserOpen;
    private JFileChooser fileChooserSave;
    private JMenuBar menuBarMain;
    private JMenu menuLookAndFeel;


    public FrameMain() {
        this.setTitle("FrameMain");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        JTableUtils.initJTableForArray(tableInput, 100, true, true, true, true);
        JTableUtils.initJTableForArray(tableOutput, 100, true, true, false, false);
        //tableOutput.setEnabled(false);
        tableInput.setRowHeight(25);
        tableOutput.setRowHeight(25);

        fileChooserOpen = new JFileChooser();
        fileChooserSave = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("."));
        fileChooserSave.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);
        fileChooserSave.addChoosableFileFilter(filter);

        fileChooserSave.setAcceptAllFileFilterUsed(false);
        fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserSave.setApproveButtonText("Save");

        menuBarMain = new JMenuBar();
        setJMenuBar(menuBarMain);

        menuLookAndFeel = new JMenu();
        menuLookAndFeel.setText("Вид");
        menuBarMain.add(menuLookAndFeel);
        SwingUtils.initLookAndFeelMenu(menuLookAndFeel);

        ru.vsu.cs.util.JTableUtils.writeArrayToJTable(tableInput, new String[][]{ {"Яна", "Женский", "1", "35"},
                                                                                  {"Давид", "Мужской", "1", "45"},
                                                                                  {"Николай", "Мужской", "1", "30"},
                                                                                  {"Александр", "Мужской", "2", "50"},
                                                                                  {"Артем", "Мужской", "2", "10"},
                                                                                  { "Евгений",	"Мужской","3","45"},
                                                                                  {"Ева", "Женский", "2", "40"},
                                                                                  {"Аня", "Женский", "2", "50"},
                                                                                  {"Настя", "Женский", "4", "50"},
                                                                                    {"Маша", "Женский", "2", "50"},
                                                                                    {"Виктор", "Мужской", "2", "40"},
                                                                                    {"Саша", "Мужской", "4", "40"}}
        );

        this.pack();


        buttonLoadInputFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                        int[][] arr = ArrayUtils.readIntArray2FromFile(fileChooserOpen.getSelectedFile().getPath());
                        JTableUtils.writeArrayToJTable(tableInput, arr);
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });
        buttonRandomInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                        int[][] matrix = ArrayUtils.createRandomIntMatrix(
                                tableInput.getRowCount(), tableInput.getColumnCount(), 100);
                        JTableUtils.writeArrayToJTable(tableInput, matrix);
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });
        buttonSaveInputInfoFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                        int[][] matrix = JTableUtils.readIntMatrixFromJTable(tableInput);
                        String file = fileChooserSave.getSelectedFile().getPath();
                        if (!file.toLowerCase().endsWith(".txt")) {
                            file += ".txt";
                        }
                        ArrayUtils.writeArrayToFile(file, matrix);
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });
        buttonSaveOutputIntoFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                        int[][] matrix = JTableUtils.readIntMatrixFromJTable(tableOutput);
                        String file = fileChooserSave.getSelectedFile().getPath();
                        if (!file.toLowerCase().endsWith(".txt")) {
                            file += ".txt";
                        }
                        ArrayUtils.writeArrayToFile(file, matrix);
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });
        buttonOperation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String[][] arr = JTableUtils.readStringMatrixFromJTable(tableInput);

                    ArrayList<Student> listArr = new ArrayList<>();
                    for (String[] arr1 : arr) {
                        String fio = String.valueOf(arr1[0]);
                        String gender = String.valueOf(arr1[1]);
                        int numCourse = Integer.parseInt(arr1[2]);
                        int middleScore = Integer.parseInt(arr1[3]);
                        Student student = new Student(fio, gender, numCourse, middleScore);
                        listArr.add(student);
                    }
                    
                    List<Student> result = Solution.searchTheBestStudent(listArr);

                    String[][] arrOut = new String[result.size()][4];
                    for (int i = 0; i < result.size(); i++) {
                        Student student = result.get(i);
                        arrOut[i][0] = student.name;
                        arrOut[i][1] = student.sex;
                        int x = student.course;
                        arrOut[i][2] = String.valueOf(x);
                        int y = student.averageScore;
                        arrOut[i][3] = String.valueOf(y);
                    }
                    JTableUtils.writeArrayToJTable(tableOutput, arrOut);
                } catch (Exception e) {
                     SwingUtils.showErrorMessageBox(e);
                }
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelMain = new JPanel();
        panelMain.setLayout(new GridLayoutManager(5, 2, new Insets(10, 10, 10, 10), 10, 10));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setVerticalScrollBarPolicy(21);
        panelMain.add(scrollPane1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 200), null, 0, false));
        tableInput = new JTable();
        scrollPane1.setViewportView(tableInput);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel1, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonLoadInputFromFile = new JButton();
        buttonLoadInputFromFile.setText("Загрузить из файла");
        panel1.add(buttonLoadInputFromFile, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonRandomInput = new JButton();
        buttonRandomInput.setText("Заполнить случайными числами");
        panel1.add(buttonRandomInput, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonSaveInputInfoFile = new JButton();
        buttonSaveInputInfoFile.setText("Сохранить в файл");
        panel1.add(buttonSaveInputInfoFile, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(100, -1), null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setVerticalScrollBarPolicy(21);
        panelMain.add(scrollPane2, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 200), null, 0, false));
        tableOutput = new JTable();
        scrollPane2.setViewportView(tableOutput);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel2, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOperation = new JButton();
        buttonOperation.setText("Переставить строки");
        panel2.add(buttonOperation, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel2.add(spacer2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel3, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonSaveOutputIntoFile = new JButton();
        buttonSaveOutputIntoFile.setText("Сохранить в файл");
        panel3.add(buttonSaveOutputIntoFile, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel3.add(spacer3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
