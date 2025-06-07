package mymod.content.ui;

// ... (所有 import 保持不變) ...
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;


public class FolderGuiDemoEnhanced extends JFrame {

    // ... (所有成員變數和大部分方法保持不變) ...
    // --- UI Components ---
    private JTextField folderPathField;
    private JButton chooseFolderBtn;
    private JButton upButton;
    private JButton saveFileBtn;
    private JButton refreshListBtn;
    private JButton openFileBtn; // 新增的按鈕
    private JButton deleteFileBtn;
    private JTextField nameField;
    private JTextField saveExtensionField;
    private JTextField filterExtensionField;
    private JTextArea contentArea;
    private JList<File> fileList;
    private DefaultListModel<File> listModel;
    private JTextArea previewArea;
    private JTextArea terminalOutputArea;
    private JTextField commandField;
    private JButton executeCmdBtn;

    // --- State ---
    private File currentFolder;

    public FolderGuiDemoEnhanced() {
        initFrame();
        initComponents();
        layoutComponents();
        addEventHandlers();
    }

    private void initFrame() {
        setTitle("Advanced File Explorer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Nimbus not available, fall back to default
        }
    }

    private void initComponents() {
        // --- Top Panel ---
        folderPathField = new JTextField();
        folderPathField.setEditable(false);
        folderPathField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        chooseFolderBtn = new JButton("選擇資料夾");
        upButton = new JButton("向上");
        upButton.setEnabled(false);

        // --- Left Panel ---
        filterExtensionField = new JTextField(10);
        filterExtensionField.setToolTipText("用逗號分隔, 例如 txt, py, ps1");
        JButton filterBtn = new JButton("套用");
        filterBtn.setEnabled(false);
        listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);
        openFileBtn = new JButton("開啟"); // 初始化按鈕
        openFileBtn.setEnabled(false);
        deleteFileBtn = new JButton("刪除");
        deleteFileBtn.setEnabled(false);

        // --- Right Panel (Save/Edit) ---
        nameField = new JTextField(15);
        saveExtensionField = new JTextField(10);
        saveExtensionField.setToolTipText("例如 txt, py, ps1");
        saveFileBtn = new JButton("儲存檔案");
        saveFileBtn.setEnabled(false);
        contentArea = new JTextArea();
        refreshListBtn = new JButton("重新整理");
        refreshListBtn.setEnabled(false);

        // --- Bottom Panel (Preview/Terminal) ---
        previewArea = new JTextArea();
        terminalOutputArea = new JTextArea();
        commandField = new JTextField();
        commandField.setToolTipText("在此輸入命令，例如 cd .., dir 等");
        executeCmdBtn = new JButton("執行");
        executeCmdBtn.setEnabled(false);
    }

    private void layoutComponents() {
        // Top Panel Layout
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        topButtonPanel.add(chooseFolderBtn);
        topButtonPanel.add(upButton);
        topPanel.add(topButtonPanel, BorderLayout.WEST);
        topPanel.add(folderPathField, BorderLayout.CENTER);

        // Left Panel Layout
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBorder(BorderFactory.createTitledBorder("篩選 (多個用逗號分隔)"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 0; filterPanel.add(new JLabel("副檔名:"), gbc);
        gbc.gridx = 1; filterPanel.add(filterExtensionField, gbc);
        JButton filterBtn = new JButton("套用");
        filterBtn.setEnabled(false);
        gbc.gridx = 2; filterPanel.add(filterBtn, gbc);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        fileList.setCellRenderer(new FileCellRenderer());
        JScrollPane listScroll = new JScrollPane(fileList);
        listScroll.setPreferredSize(new Dimension(250, 0));
        JPanel leftBottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        leftBottomPanel.add(openFileBtn); // 將按鈕加入面板
        leftBottomPanel.add(deleteFileBtn);
        leftPanel.add(filterPanel, BorderLayout.NORTH);
        leftPanel.add(listScroll, BorderLayout.CENTER);
        leftPanel.add(leftBottomPanel, BorderLayout.SOUTH);

        // Right Panel Layout
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        JPanel savePanel = new JPanel(new GridBagLayout());
        savePanel.setBorder(BorderFactory.createTitledBorder("儲存/編輯檔案"));
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 5, 5, 5);
        gbc2.gridx = 0; gbc2.gridy = 0; savePanel.add(new JLabel("檔名:"), gbc2);
        gbc2.gridx = 1; gbc2.gridwidth = 2; gbc2.fill = GridBagConstraints.HORIZONTAL; savePanel.add(nameField, gbc2);
        gbc2.gridx = 0; gbc2.gridy = 1; gbc2.gridwidth = 1; gbc2.fill = GridBagConstraints.NONE; savePanel.add(new JLabel("副檔名:"), gbc2);
        gbc2.gridx = 1; savePanel.add(saveExtensionField, gbc2);
        gbc2.gridx = 3; gbc2.gridy = 0; gbc2.gridheight = 2; gbc2.fill = GridBagConstraints.VERTICAL; savePanel.add(saveFileBtn, gbc2);
        contentArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        contentArea.setLineWrap(true);
        contentArea.setBorder(BorderFactory.createTitledBorder("檔案內容 (可編輯)"));
        JScrollPane textScroll = new JScrollPane(contentArea);
        JPanel bottomRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        bottomRightPanel.add(refreshListBtn);
        rightPanel.add(savePanel, BorderLayout.NORTH);
        rightPanel.add(textScroll, BorderLayout.CENTER);
        rightPanel.add(bottomRightPanel, BorderLayout.SOUTH);

        // Split Panes
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        mainSplitPane.setDividerLocation(300);

        // Bottom Tabbed Pane
        JTabbedPane bottomTabbedPane = new JTabbedPane();
        previewArea.setEditable(false);
        previewArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        previewArea.setLineWrap(true);
        JScrollPane previewScroll = new JScrollPane(previewArea);
        previewScroll.setBorder(BorderFactory.createTitledBorder("預覽內容"));
        bottomTabbedPane.addTab("預覽", previewScroll);

        JPanel terminalPanel = new JPanel(new BorderLayout(5, 5));
        terminalOutputArea.setEditable(false);
        terminalOutputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane terminalScroll = new JScrollPane(terminalOutputArea);
        terminalScroll.setBorder(BorderFactory.createTitledBorder("終端輸出"));
        JPanel cmdPanel = new JPanel(new BorderLayout(5, 5));
        cmdPanel.add(new JLabel("命令: "), BorderLayout.WEST);
        cmdPanel.add(commandField, BorderLayout.CENTER);
        cmdPanel.add(executeCmdBtn, BorderLayout.EAST);
        terminalPanel.add(terminalScroll, BorderLayout.CENTER);
        terminalPanel.add(cmdPanel, BorderLayout.SOUTH);
        bottomTabbedPane.addTab("終端模擬", terminalPanel);
        bottomTabbedPane.setPreferredSize(new Dimension(0, 250));

        // Add to Content Pane
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(topPanel, BorderLayout.NORTH);
        cp.add(mainSplitPane, BorderLayout.CENTER);
        cp.add(bottomTabbedPane, BorderLayout.SOUTH);
    }

    // ... (addEventHandlers 和其他 UI 方法保持不變) ...
    private void addEventHandlers() {
        chooseFolderBtn.addActionListener(e -> chooseFolder());
        upButton.addActionListener(e -> navigateUp());
        refreshListBtn.addActionListener(e -> refreshFileList());
        filterExtensionField.addActionListener(e -> refreshFileList()); // Press enter to filter
        openFileBtn.addActionListener(e -> openSelectedFile());
        deleteFileBtn.addActionListener(e -> deleteSelectedFile());
        saveFileBtn.addActionListener(e -> saveFile());
        executeCmdBtn.addActionListener(e -> executeCommand());
        commandField.addActionListener(e -> executeCommand());

        // 單擊事件：處理預覽和按鈕狀態
        fileList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                File selected = fileList.getSelectedValue();
                boolean isSelected = (selected != null);
                openFileBtn.setEnabled(isSelected);
                deleteFileBtn.setEnabled(isSelected);

                if (selected != null) {
                    if (selected.isFile()) {
                        previewFile(selected);
                    } else {
                        previewArea.setText("這是一個資料夾。\n雙擊進入，或點擊 '開啟' 按鈕來瀏覽。");
                        previewArea.setCaretPosition(0);
                    }
                } else {
                    previewArea.setText(""); // 清空預覽
                }
            }
        });

        // 雙擊事件：只處理資料夾導航
        fileList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                // 我們只關心雙擊事件
                if (evt.getClickCount() == 2) {
                    int index = fileList.locationToIndex(evt.getPoint());
                    if (index != -1) {
                        File selected = listModel.getElementAt(index);
                        // 如果雙擊的是資料夾，則進入
                        if (selected.isDirectory()) {
                            setCurrentFolder(selected);
                        }
                        // 如果雙擊的是檔案，則不做任何事，鼓勵用戶使用按鈕或編輯區
                    }
                }
            }
        });
    }

    private void chooseFolder() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            setCurrentFolder(chooser.getSelectedFile());
        }
    }

    private void navigateUp() {
        if (currentFolder != null) {
            File parent = currentFolder.getParentFile();
            if (parent != null && parent.isDirectory()) {
                setCurrentFolder(parent);
            }
        }
    }

    // ==========================================================
    // =====            核心改動方法 (executeCommand)         =====
    // ==========================================================

    /**
     * Executes a command. Intercepts 'cd' to handle it internally,
     * otherwise executes the command in a separate process.
     */
    private void executeCommand() {
        String cmd = commandField.getText().trim();
        if (cmd.isEmpty() || currentFolder == null) return;

        // --- 核心邏輯：攔截 cd 命令 ---
        if (cmd.toLowerCase().startsWith("cd ") || cmd.equalsIgnoreCase("cd")) {
            handleCdCommand(cmd);
        } else {
            // 對於所有其他命令，使用 SwingWorker 在背景執行
            executeExternalCommand(cmd);
        }
    }

    /**
     * Handles the 'cd' command internally to change the application's current directory.
     * @param cmd The full command string (e.g., "cd my_folder").
     */
    private void handleCdCommand(String cmd) {
        terminalOutputArea.append("$ " + cmd + "\n");
        commandField.setText("");

        String targetPathStr = cmd.length() > 2 ? cmd.substring(3).trim() : System.getProperty("user.home");

        // 使用 java.nio.Path 來安全地解析路徑，能很好地處理 ".." 和 "."
        try {
            Path newPath = currentFolder.toPath().resolve(targetPathStr).normalize();
            File newDir = newPath.toFile();

            if (newDir.exists() && newDir.isDirectory()) {
                setCurrentFolder(newDir);
            } else {
                terminalOutputArea.append("cd: no such file or directory: " + targetPathStr + "\n");
            }
        } catch (InvalidPathException e) {
            terminalOutputArea.append("cd: invalid path: " + targetPathStr + "\n");
        }

        terminalOutputArea.setCaretPosition(terminalOutputArea.getDocument().getLength());
    }

    /**
     * Executes an external command in a background thread.
     * @param cmd The command to execute.
     */
    private void executeExternalCommand(String cmd) {
        executeCmdBtn.setEnabled(false);
        commandField.setEditable(false);
        terminalOutputArea.append("$ " + cmd + "\n");

        SwingWorker<String, Void> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() throws Exception {
                ProcessBuilder builder = new ProcessBuilder();
                builder.directory(currentFolder); // << 關鍵：使用同步後的 currentFolder
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    builder.command("powershell.exe", "-NoLogo", "-Command", "[Console]::OutputEncoding=[Text.Encoding]::UTF8; " + cmd);
                } else {
                    builder.command("bash", "-c", cmd);
                }

                Process process = builder.start();
                StringBuilder output = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
                     BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8))) {

                    String line;
                    while ((line = reader.readLine()) != null) output.append(line).append("\n");
                    while ((line = errorReader.readLine()) != null) output.append(line).append("\n");
                }
                process.waitFor();
                return output.toString();
            }

            @Override
            protected void done() {
                try {
                    terminalOutputArea.append(get());
                } catch (Exception ex) {
                    terminalOutputArea.append("Execution failed: " + ex.getMessage() + "\n");
                } finally {
                    terminalOutputArea.append("\n");
                    terminalOutputArea.setCaretPosition(terminalOutputArea.getDocument().getLength());
                    commandField.setText("");
                    executeCmdBtn.setEnabled(true);
                    commandField.setEditable(true);
                }
            }
        };
        worker.execute();
    }

    // ... (其他方法，如 setCurrentFolder, refreshFileList 等，保持不變) ...
    private void setCurrentFolder(File folder) {
        currentFolder = folder;
        folderPathField.setText(currentFolder.getAbsolutePath());

        // Enable/Disable components
        boolean folderSelected = (currentFolder != null);
        saveFileBtn.setEnabled(folderSelected);
        refreshListBtn.setEnabled(folderSelected);
        executeCmdBtn.setEnabled(folderSelected);
        upButton.setEnabled(currentFolder.getParentFile() != null);
        openFileBtn.setEnabled(false);
        deleteFileBtn.setEnabled(false);

        // Clear fields
        nameField.setText("");
        saveExtensionField.setText("");
        contentArea.setText("");
        previewArea.setText("");
        // 不要清除 commandField，但可以將焦點設回
        commandField.requestFocusInWindow();

        refreshFileList();
    }

    private void refreshFileList() {
        if (currentFolder == null) return;

        setBusy(true, "Refreshing...");
        String filterText = filterExtensionField.getText().trim();

        SwingWorker<DefaultListModel<File>, Void> worker = new SwingWorker<>() {
            @Override
            protected DefaultListModel<File> doInBackground() throws Exception {
                DefaultListModel<File> newModel = new DefaultListModel<>();
                File[] files = currentFolder.listFiles();
                if (files == null) return newModel;

                Set<String> extensions = new HashSet<>();
                if (!filterText.isEmpty()) {
                    Arrays.stream(filterText.split(","))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .forEach(extensions::add);
                }

                Arrays.sort(files, (f1, f2) -> {
                    if (f1.isDirectory() && !f2.isDirectory()) return -1;
                    if (!f1.isDirectory() && f2.isDirectory()) return 1;
                    return f1.getName().compareToIgnoreCase(f2.getName());
                });

                for (File f : files) {
                    if (extensions.isEmpty()) {
                        newModel.addElement(f);
                    } else {
                        if (f.isDirectory()) {
                            newModel.addElement(f);
                        } else {
                            String name = f.getName();
                            int dot = name.lastIndexOf('.');
                            if (dot != -1) {
                                String fileExt = name.substring(dot + 1);
                                if (extensions.stream().anyMatch(fileExt::equalsIgnoreCase)) {
                                    newModel.addElement(f);
                                }
                            }
                        }
                    }
                }
                return newModel;
            }

            @Override
            protected void done() {
                try {
                    listModel = get();
                    fileList.setModel(listModel);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(FolderGuiDemoEnhanced.this, "Error refreshing file list: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    setBusy(false, null);
                }
            }
        };
        worker.execute();
    }

    private void previewFile(File file) {
        previewArea.setText("Loading preview for " + file.getName() + "...");

        SwingWorker<String, Void> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() throws Exception {
                if (isLikelyPlainText(file)) {
                    StringBuilder sb = new StringBuilder();
                    // Preview only first 1000 lines to avoid freezing on huge files
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                        String line;
                        int lineCount = 0;
                        while ((line = reader.readLine()) != null && lineCount < 1000) {
                            sb.append(line).append("\n");
                            lineCount++;
                        }
                        if (line != null) {
                            sb.append("\n... (file is too long to display fully in preview)");
                        }
                    }
                    return sb.toString();
                } else {
                    return "Cannot preview this file type (Binary or unsupported). \nUse the 'Open' button to open with the default application.";
                }
            }

            @Override
            protected void done() {
                try {
                    previewArea.setText(get());
                } catch (Exception ex) {
                    previewArea.setText("Failed to read file: " + ex.getMessage());
                } finally {
                    previewArea.setCaretPosition(0);
                }
            }
        };
        worker.execute();
    }

    private void openSelectedFile() {
        File selectedFile = fileList.getSelectedValue();
        if (selectedFile == null) return;

        if (!Desktop.isDesktopSupported()) {
            JOptionPane.showMessageDialog(this, "Desktop operations not supported on this system.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        setBusy(true, "Opening " + selectedFile.getName() + "...");
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                Desktop.getDesktop().open(selectedFile);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get(); // Check for exceptions
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(FolderGuiDemoEnhanced.this, "Could not open file: " + ex.getCause().getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    setBusy(false, null);
                }
            }
        };
        worker.execute();
    }

    private void saveFile() {
        if (currentFolder == null) {
            JOptionPane.showMessageDialog(this, "Please select a folder first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String baseName = nameField.getText().trim();
        String ext = saveExtensionField.getText().trim();
        if (baseName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a file name!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (ext.isEmpty()) ext = "txt";
        if (ext.startsWith(".")) ext = ext.substring(1);

        String fileName = baseName + "." + ext;
        File outFile = new File(currentFolder, fileName);
        String content = contentArea.getText();

        setBusy(true, "Saving...");
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws IOException {
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), StandardCharsets.UTF_8))) {
                    writer.write(content);
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    get(); // Check for exceptions
                    JOptionPane.showMessageDialog(FolderGuiDemoEnhanced.this, "File saved successfully: " + outFile.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshFileList();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(FolderGuiDemoEnhanced.this, "Failed to save file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    setBusy(false, null);
                }
            }
        };
        worker.execute();
    }

    private void deleteSelectedFile() {
        File selectedFile = fileList.getSelectedValue();
        if (selectedFile == null) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete '" + selectedFile.getName() + "'?", "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        setBusy(true, "Deleting...");
        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                if (selectedFile.isDirectory()) {
                    // Simple deletion: only delete empty directories for safety
                    if (selectedFile.listFiles().length > 0) {
                        throw new IOException("Directory is not empty.");
                    }
                }
                return selectedFile.delete();
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        JOptionPane.showMessageDialog(FolderGuiDemoEnhanced.this, "Deleted: " + selectedFile.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
                        previewArea.setText("");
                        refreshFileList();
                    } else {
                        throw new Exception("File could not be deleted. It may be in use.");
                    }
                } catch (Exception ex) {
                    String message = (ex.getCause() != null) ? ex.getCause().getMessage() : ex.getMessage();
                    JOptionPane.showMessageDialog(FolderGuiDemoEnhanced.this, "Deletion failed: " + message, "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    setBusy(false, null);
                }
            }
        };
        worker.execute();
    }

    private void setBusy(boolean busy, String message) {
        if (busy) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (message != null) {
                previewArea.setText(message);
            }
        } else {
            setCursor(Cursor.getDefaultCursor());
        }
        // Disable/enable key buttons during long operations
        chooseFolderBtn.setEnabled(!busy);
        upButton.setEnabled(!busy && currentFolder != null && currentFolder.getParentFile() != null);
        saveFileBtn.setEnabled(!busy && currentFolder != null);
        refreshListBtn.setEnabled(!busy && currentFolder != null);
        deleteFileBtn.setEnabled(!busy && fileList.getSelectedIndex() != -1);
        openFileBtn.setEnabled(!busy && fileList.getSelectedIndex() != -1);
        executeCmdBtn.setEnabled(!busy && currentFolder != null);
    }

    private boolean isLikelyPlainText(File file) {
        // A simple heuristic to guess if a file is plain text.
        String name = file.getName().toLowerCase();
        Set<String> textExtensions = new HashSet<>(Arrays.asList(
                "txt", "java", "py", "c", "cpp", "h", "hpp", "js", "html", "css",
                "xml", "json", "yml", "yaml", "md", "sh", "ps1", "log"
        ));
        int dot = name.lastIndexOf('.');
        if (dot != -1) {
            String ext = name.substring(dot + 1);
            return textExtensions.contains(ext);
        }
        return false;
    }

    private static class FileCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof File) {
                File file = (File) value;
                setText(file.getName());
                setIcon(FileSystemView.getFileSystemView().getSystemIcon(file));
                if (file.isDirectory()) {
                    setFont(getFont().deriveFont(Font.BOLD));
                } else {
                    setFont(getFont().deriveFont(Font.PLAIN));
                }
            }
            return this;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FolderGuiDemoEnhanced demo = new FolderGuiDemoEnhanced();
            demo.setVisible(true);
        });
    }
}