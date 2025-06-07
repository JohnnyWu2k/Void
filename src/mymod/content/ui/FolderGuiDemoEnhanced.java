package mymod.content.ui;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class FolderGuiDemoEnhanced extends JFrame {
    private JTextField folderPathField;
    private JButton chooseFolderBtn;
    private JButton saveFileBtn;
    private JButton refreshListBtn;
    private JButton filterBtn;
    private JButton deleteFileBtn;
    private JTextField nameField;
    private JTextField saveExtensionField;
    private JTextField filterExtensionField;
    private JTextArea contentArea;
    private JList<File> fileList;
    private DefaultListModel<File> listModel;
    private File currentFolder;
    private JTextArea previewArea;
    private JTextArea terminalOutputArea;
    private JTextField commandField;
    private JButton executeCmdBtn;

    public FolderGuiDemoEnhanced() {
        setTitle("資料夾管理器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 800);
        setLocationRelativeTo(null);
        try { for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) { if ("Nimbus".equals(info.getName())) { UIManager.setLookAndFeel(info.getClassName()); break; } } } catch (Exception e) {}
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        folderPathField = new JTextField();
        folderPathField.setEditable(false);
        folderPathField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        chooseFolderBtn = new JButton("選擇資料夾");
        topPanel.add(folderPathField, BorderLayout.CENTER);
        topPanel.add(chooseFolderBtn, BorderLayout.EAST);
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBorder(BorderFactory.createTitledBorder("篩選"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        filterPanel.add(new JLabel("副檔名："), gbc);
        filterExtensionField = new JTextField(8);
        filterExtensionField.setToolTipText("例如 txt, py, ps1");
        gbc.gridx = 1;
        filterPanel.add(filterExtensionField, gbc);
        filterBtn = new JButton("套用");
        filterBtn.setEnabled(false);
        gbc.gridx = 2;
        filterPanel.add(filterBtn, gbc);
        listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        fileList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof File) {
                    File f = (File) value;
                    setText(f.getName());
                    Icon icon = FileSystemView.getFileSystemView().getSystemIcon(f);
                    setIcon(icon);
                }
                return this;
            }
        });
        JScrollPane listScroll = new JScrollPane(fileList);
        listScroll.setPreferredSize(new Dimension(250, 0));
        deleteFileBtn = new JButton("刪除");
        deleteFileBtn.setEnabled(false);
        JPanel deletePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        deletePanel.add(deleteFileBtn);
        leftPanel.add(filterPanel, BorderLayout.NORTH);
        leftPanel.add(listScroll, BorderLayout.CENTER);
        leftPanel.add(deletePanel, BorderLayout.SOUTH);
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        JPanel savePanel = new JPanel(new GridBagLayout());
        savePanel.setBorder(BorderFactory.createTitledBorder("儲存檔案"));
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 5, 5, 5);
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        savePanel.add(new JLabel("檔名："), gbc2);
        nameField = new JTextField(10);
        gbc2.gridx = 1;
        savePanel.add(nameField, gbc2);
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        savePanel.add(new JLabel("副檔名："), gbc2);
        saveExtensionField = new JTextField(8);
        saveExtensionField.setToolTipText("例如 txt, py, ps1");
        gbc2.gridx = 1;
        savePanel.add(saveExtensionField, gbc2);
        saveFileBtn = new JButton("儲存檔案");
        saveFileBtn.setEnabled(false);
        gbc2.gridx = 2;
        gbc2.gridy = 0;
        gbc2.gridheight = 2;
        savePanel.add(saveFileBtn, gbc2);
        contentArea = new JTextArea();
        contentArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        contentArea.setLineWrap(true);
        contentArea.setBorder(BorderFactory.createTitledBorder("檔案內容"));
        JScrollPane textScroll = new JScrollPane(contentArea);
        refreshListBtn = new JButton("重新整理");
        refreshListBtn.setEnabled(false);
        JPanel bottomRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        bottomRightPanel.add(refreshListBtn);
        rightPanel.add(savePanel, BorderLayout.NORTH);
        rightPanel.add(textScroll, BorderLayout.CENTER);
        rightPanel.add(bottomRightPanel, BorderLayout.SOUTH);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(300);
        JTabbedPane bottomTabbedPane = new JTabbedPane();
        previewArea = new JTextArea();
        previewArea.setEditable(false);
        previewArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        previewArea.setBorder(BorderFactory.createTitledBorder("預覽內容"));
        JScrollPane previewScroll = new JScrollPane(previewArea);
        bottomTabbedPane.addTab("預覽", previewScroll);
        JPanel terminalPanel = new JPanel(new BorderLayout(5, 5));
        terminalOutputArea = new JTextArea();
        terminalOutputArea.setEditable(false);
        terminalOutputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane terminalScroll = new JScrollPane(terminalOutputArea);
        terminalScroll.setBorder(BorderFactory.createTitledBorder("終端輸出"));
        JPanel cmdPanel = new JPanel(new BorderLayout(5, 5));
        JLabel cmdLabel = new JLabel("命令：");
        cmdLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        commandField = new JTextField();
        commandField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        commandField.setToolTipText("在此輸入命令，例如 dir, python hello.py 等");
        executeCmdBtn = new JButton("執行");
        cmdPanel.add(cmdLabel, BorderLayout.WEST);
        cmdPanel.add(commandField, BorderLayout.CENTER);
        cmdPanel.add(executeCmdBtn, BorderLayout.EAST);
        terminalPanel.add(terminalScroll, BorderLayout.CENTER);
        terminalPanel.add(cmdPanel, BorderLayout.SOUTH);
        bottomTabbedPane.addTab("終端模擬", terminalPanel);
        bottomTabbedPane.setPreferredSize(new Dimension(0, 300));
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(topPanel, BorderLayout.NORTH);
        cp.add(splitPane, BorderLayout.CENTER);
        cp.add(bottomTabbedPane, BorderLayout.SOUTH);
        chooseFolderBtn.addActionListener(e -> { JFileChooser chooser = new JFileChooser(); chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); int result = chooser.showOpenDialog(FolderGuiDemoEnhanced.this); if (result == JFileChooser.APPROVE_OPTION) { currentFolder = chooser.getSelectedFile(); folderPathField.setText(currentFolder.getAbsolutePath()); saveFileBtn.setEnabled(true); refreshListBtn.setEnabled(true); filterBtn.setEnabled(true); deleteFileBtn.setEnabled(false); nameField.setText(""); saveExtensionField.setText(""); filterExtensionField.setText(""); contentArea.setText(""); previewArea.setText(""); terminalOutputArea.setText(""); refreshFileList(null); } });
        saveFileBtn.addActionListener(e -> { if (currentFolder == null) { JOptionPane.showMessageDialog(FolderGuiDemoEnhanced.this, "請先選擇資料夾！", "錯誤", JOptionPane.ERROR_MESSAGE); return; } String baseName = nameField.getText().trim(); String ext = saveExtensionField.getText().trim(); if (baseName.isEmpty()) { JOptionPane.showMessageDialog(FolderGuiDemoEnhanced.this, "請輸入檔名！", "錯誤", JOptionPane.ERROR_MESSAGE); return; } if (ext.isEmpty()) ext = "txt"; if (ext.startsWith(".")) ext = ext.substring(1); String fileName = baseName + "." + ext; File outFile = new File(currentFolder, fileName); try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), StandardCharsets.UTF_8))) { writer.write(contentArea.getText()); JOptionPane.showMessageDialog(FolderGuiDemoEnhanced.this, "已寫入：" + outFile.getName(), "完成", JOptionPane.INFORMATION_MESSAGE); refreshFileList(filterExtensionField.getText().trim()); } catch (IOException ex) { JOptionPane.showMessageDialog(FolderGuiDemoEnhanced.this, "寫入失敗：" + ex.getMessage(), "錯誤", JOptionPane.ERROR_MESSAGE); } });
        refreshListBtn.addActionListener(e -> refreshFileList(filterExtensionField.getText().trim()));
        filterBtn.addActionListener(e -> { String ext = filterExtensionField.getText().trim(); if (currentFolder == null) return; refreshFileList(ext); });
        deleteFileBtn.addActionListener(e -> { int idx = fileList.getSelectedIndex(); if (idx == -1) { JOptionPane.showMessageDialog(FolderGuiDemoEnhanced.this, "請先選擇檔案！", "錯誤", JOptionPane.ERROR_MESSAGE); return; } String fileName = listModel.getElementAt(idx).getName(); File chosen = new File(currentFolder, fileName); int confirm = JOptionPane.showConfirmDialog(FolderGuiDemoEnhanced.this, "確定刪除 '" + fileName + "'?", "確認", JOptionPane.YES_NO_OPTION); if (confirm == JOptionPane.YES_OPTION) { if (chosen.delete()) { JOptionPane.showMessageDialog(FolderGuiDemoEnhanced.this, "已刪除：" + fileName, "完成", JOptionPane.INFORMATION_MESSAGE); previewArea.setText(""); refreshFileList(filterExtensionField.getText().trim()); } else { JOptionPane.showMessageDialog(FolderGuiDemoEnhanced.this, "刪除失敗！", "錯誤", JOptionPane.ERROR_MESSAGE); } } });
        fileList.addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) { int idx = fileList.getSelectedIndex(); if (idx != -1) { deleteFileBtn.setEnabled(true); File chosen = listModel.getElementAt(idx); if (chosen.isFile()) { String lower = chosen.getName().toLowerCase(); String extInput = filterExtensionField.getText().trim().toLowerCase(); if (lower.endsWith(".txt") || (!extInput.isEmpty() && lower.endsWith("." + extInput))) { StringBuilder sb = new StringBuilder(); try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(chosen), StandardCharsets.UTF_8))) { String line; while ((line = reader.readLine()) != null) sb.append(line).append("\n"); previewArea.setText(sb.toString()); } catch (IOException ioEx) { previewArea.setText("讀取失敗：" + ioEx.getMessage()); } } else { previewArea.setText("無法預覽"); } } else { folderPathField.setText(chosen.getAbsolutePath()); currentFolder = chosen; deleteFileBtn.setEnabled(false); nameField.setText(""); saveExtensionField.setText(""); filterExtensionField.setText(""); contentArea.setText(""); previewArea.setText(""); terminalOutputArea.setText(""); commandField.setText(""); refreshFileList(null); } } else { deleteFileBtn.setEnabled(false); previewArea.setText(""); } } });
        executeCmdBtn.addActionListener(e -> executeCommand());
        commandField.addActionListener(e -> executeCommand());
    }
    private void refreshFileList(String ext) {
        listModel.clear();
        if (currentFolder != null && currentFolder.isDirectory()) {
            File[] files = currentFolder.listFiles();
            if (files != null) for (File f : files) {
                if (ext == null || ext.isEmpty()) listModel.addElement(f);
                else {
                    String norm = ext.startsWith(".") ? ext.substring(1) : ext;
                    if (f.isFile()) {
                        String name = f.getName(); int dot = name.lastIndexOf('.');
                        if (dot != -1) {
                            String fileExt = name.substring(dot + 1);
                            if (fileExt.equalsIgnoreCase(norm)) listModel.addElement(f);
                        }
                    } else listModel.addElement(f);
                }
            }
        }
    }
    private void executeCommand() {
        String cmd = commandField.getText().trim(); if (cmd.isEmpty() || currentFolder == null) return;
        try {
            ProcessBuilder builder = new ProcessBuilder(); builder.directory(currentFolder);
            if (System.getProperty("os.name").toLowerCase().contains("win")) builder.command("powershell.exe", "-NoLogo", "-Command", "[Console]::OutputEncoding=[Text.Encoding]::UTF8; " + cmd);
            else builder.command("bash", "-c", cmd);
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) output.append(line).append("\n");
            while ((line = errorReader.readLine()) != null) output.append(line).append("\n");
            process.waitFor();
            terminalOutputArea.append("$ " + cmd + "\n");
            terminalOutputArea.append(output.toString());
            terminalOutputArea.append("\n");
            commandField.setText("");
            terminalOutputArea.setCaretPosition(terminalOutputArea.getDocument().getLength());
        } catch (Exception ex) { terminalOutputArea.append("執行失敗：" + ex.getMessage() + "\n"); }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { FolderGuiDemoEnhanced demo = new FolderGuiDemoEnhanced(); demo.setVisible(true); });
    }
}
