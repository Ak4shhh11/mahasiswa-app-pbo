import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Aplikasi Biodata Mahasiswa
 * Tema UI/UX  : Futuristic - Cozy - Simple
 * Library     : 100% Java Swing bawaan JDK (TIDAK ada dependency eksternal)
 *
 * Logika program:
 *  - Tombol "Tampilkan" membaca isi field NIM, Nama, Program Studi
 *    lalu menampilkannya di panel Output dengan animasi reveal per baris.
 *  - Tombol "Reset" mengosongkan field input dan panel Output.
 */
public class BiodataMahasiswaApp extends JFrame {

    // ===================== PALET WARNA (FUTURISTIC + COZY) =====================
    private static final Color BG_TOP        = new Color(14, 16, 26);
    private static final Color BG_BOTTOM     = new Color(26, 21, 43);
    private static final Color CARD_BG       = new Color(30, 32, 48);
    private static final Color OUTPUT_BG     = new Color(13, 15, 24);
    private static final Color INPUT_BG      = new Color(40, 43, 61);
    private static final Color BORDER_NORMAL = new Color(70, 74, 100);
    private static final Color ACCENT_CYAN   = new Color(0, 224, 199);
    private static final Color ACCENT_PURPLE = new Color(150, 110, 230);
    private static final Color ACCENT_GREEN  = new Color(120, 220, 170);
    private static final Color WARNING_COLOR = new Color(255, 138, 128);
    private static final Color TEXT_LIGHT    = new Color(232, 232, 242);
    private static final Color TEXT_MUTED    = new Color(150, 154, 176);

    private RoundedField txtNim;
    private RoundedField txtNama;
    private RoundedField txtProdi;
    private JTextPane outputPane;
    private StyledDocument outputDoc;
    private javax.swing.Timer revealTimer;

    public BiodataMahasiswaApp() {
        setTitle("Aplikasi Biodata Mahasiswa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(620, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        buildUI();
        showPlaceholder();
    }

    // ===================== SUSUNAN UI =====================

    private void buildUI() {
        GradientPanel root = new GradientPanel(BG_TOP, BG_BOTTOM);
        root.setLayout(new BorderLayout());
        setContentPane(root);

        root.add(buildHeader(), BorderLayout.NORTH);
        root.add(buildContent(), BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(BorderFactory.createEmptyBorder(30, 0, 14, 0));

        JLabel title = new JLabel("Aplikasi Biodata Mahasiswa");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(TEXT_LIGHT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Sistem Input & Tampilan Data Mahasiswa");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel accentBar = new JPanel();
        accentBar.setBackground(ACCENT_CYAN);
        accentBar.setMaximumSize(new Dimension(64, 4));
        accentBar.setPreferredSize(new Dimension(64, 4));
        accentBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(title);
        header.add(Box.createVerticalStrut(6));
        header.add(subtitle);
        header.add(Box.createVerticalStrut(14));
        header.add(accentBar);
        return header;
    }

    private JPanel buildContent() {
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(6, 40, 30, 40));

        content.add(buildInputCard());
        content.add(Box.createVerticalStrut(22));
        content.add(buildButtonPanel());
        content.add(Box.createVerticalStrut(22));
        content.add(buildOutputCard());

        return content;
    }

    private JPanel buildInputCard() {
        RoundedPanel card = new RoundedPanel(CARD_BG, 24);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(24, 26, 10, 26));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.setMaximumSize(new Dimension(4000, 340));

        JLabel cardTitle = new JLabel("INPUT DATA");
        cardTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        cardTitle.setForeground(ACCENT_CYAN);
        cardTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(cardTitle);
        card.add(Box.createVerticalStrut(16));

        txtNim = new RoundedField(ACCENT_CYAN, INPUT_BG, BORDER_NORMAL, TEXT_LIGHT);
        txtNama = new RoundedField(ACCENT_PURPLE, INPUT_BG, BORDER_NORMAL, TEXT_LIGHT);
        txtProdi = new RoundedField(ACCENT_CYAN, INPUT_BG, BORDER_NORMAL, TEXT_LIGHT);

        card.add(buildFieldGroup("NIM", IconBadge.Type.ID, ACCENT_CYAN, ACCENT_PURPLE, txtNim));
        card.add(buildFieldGroup("NAMA", IconBadge.Type.PERSON, ACCENT_PURPLE, ACCENT_CYAN, txtNama));
        card.add(buildFieldGroup("PROGRAM STUDI", IconBadge.Type.GRAD, ACCENT_GREEN, ACCENT_CYAN, txtProdi));

        return card;
    }

    private JPanel buildFieldGroup(String labelText, IconBadge.Type type, Color c1, Color c2, RoundedField field) {
        JPanel wrap = new JPanel();
        wrap.setOpaque(false);
        wrap.setLayout(new BoxLayout(wrap, BoxLayout.Y_AXIS));
        wrap.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrap.setMaximumSize(new Dimension(4000, 90));

        JPanel labelRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        labelRow.setOpaque(false);
        labelRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        IconBadge badge = new IconBadge(type, c1, c2);
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(TEXT_MUTED);

        labelRow.add(badge);
        labelRow.add(lbl);

        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(4000, 42));
        field.setPreferredSize(new Dimension(400, 42));

        wrap.add(labelRow);
        wrap.add(Box.createVerticalStrut(6));
        wrap.add(field);
        wrap.add(Box.createVerticalStrut(14));

        return wrap;
    }

    private JPanel buildButtonPanel() {
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        btnPanel.setOpaque(false);
        btnPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        GradientButton btnTampilkan = new GradientButton("Tampilkan", ACCENT_CYAN, ACCENT_PURPLE);
        GradientButton btnReset = new GradientButton("Reset", new Color(96, 100, 128), new Color(62, 66, 92));

        btnTampilkan.addActionListener(e -> onTampilkan());
        btnReset.addActionListener(e -> onReset());

        btnPanel.add(btnTampilkan);
        btnPanel.add(btnReset);

        return btnPanel;
    }

    private JPanel buildOutputCard() {
        RoundedPanel card = new RoundedPanel(OUTPUT_BG, 24);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel outLabel = new JLabel("OUTPUT");
        outLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        outLabel.setForeground(ACCENT_PURPLE);

        outputPane = new JTextPane();
        outputPane.setEditable(false);
        outputPane.setOpaque(false);
        outputPane.setFont(new Font("Consolas", Font.PLAIN, 14));
        outputPane.setBorder(BorderFactory.createEmptyBorder(12, 2, 0, 2));
        outputDoc = outputPane.getStyledDocument();

        JScrollPane scroll = new JScrollPane(outputPane);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setPreferredSize(new Dimension(400, 190));

        card.add(outLabel, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);

        return card;
    }

    // ===================== LOGIKA APLIKASI =====================

    private void onTampilkan() {
        if (revealTimer != null && revealTimer.isRunning()) {
            revealTimer.stop();
        }

        String nim = txtNim.getText().trim();
        String nama = txtNama.getText().trim();
        String prodi = txtProdi.getText().trim();

        clearOutput();

        if (nim.isEmpty() || nama.isEmpty() || prodi.isEmpty()) {
            insertStyled("[!] Harap isi semua data terlebih dahulu!", WARNING_COLOR, true);
            return;
        }

        List<Seg> segs = new ArrayList<>();
        segs.add(new Seg("================================\n        BIODATA MAHASISWA\n================================\n\n", ACCENT_PURPLE, true));
        segs.add(new Seg("NIM           : ", TEXT_MUTED, false));
        segs.add(new Seg(nim + "\n", ACCENT_CYAN, true));
        segs.add(new Seg("Nama          : ", TEXT_MUTED, false));
        segs.add(new Seg(nama + "\n", ACCENT_PURPLE, true));
        segs.add(new Seg("Program Studi : ", TEXT_MUTED, false));
        segs.add(new Seg(prodi + "\n", ACCENT_GREEN, true));

        revealSegments(segs);
    }

    private void onReset() {
        if (revealTimer != null && revealTimer.isRunning()) {
            revealTimer.stop();
        }
        txtNim.setText("");
        txtNama.setText("");
        txtProdi.setText("");
        clearOutput();
        showPlaceholder();
        txtNim.requestFocusInWindow();
    }

    private void showPlaceholder() {
        insertStyled("Output akan muncul di sini setelah klik \"Tampilkan\"...", TEXT_MUTED, false);
    }

    private void revealSegments(List<Seg> segs) {
        revealTimer = new javax.swing.Timer(140, null);
        final int[] index = {0};
        revealTimer.addActionListener(e -> {
            if (index[0] >= segs.size()) {
                revealTimer.stop();
                return;
            }
            Seg s = segs.get(index[0]);
            insertStyled(s.text, s.color, s.bold);
            index[0]++;
        });
        revealTimer.setInitialDelay(0);
        revealTimer.start();
    }

    private void insertStyled(String text, Color color, boolean bold) {
        try {
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setForeground(attrs, color);
            StyleConstants.setBold(attrs, bold);
            StyleConstants.setFontFamily(attrs, "Consolas");
            StyleConstants.setFontSize(attrs, 14);
            outputDoc.insertString(outputDoc.getLength(), text, attrs);
            outputPane.setCaretPosition(outputDoc.getLength());
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    private void clearOutput() {
        try {
            outputDoc.remove(0, outputDoc.getLength());
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    // Segment teks berwarna untuk animasi reveal di panel Output
    private static class Seg {
        final String text;
        final Color color;
        final boolean bold;

        Seg(String text, Color color, boolean bold) {
            this.text = text;
            this.color = color;
            this.bold = bold;
        }
    }

    // ===================== MAIN =====================
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // abaikan, gunakan look and feel default jika gagal
        }
        SwingUtilities.invokeLater(() -> {
            BiodataMahasiswaApp app = new BiodataMahasiswaApp();
            app.setVisible(true);
        });
    }
}

// ===================== PANEL GRADIENT UNTUK BACKGROUND =====================
class GradientPanel extends JPanel {
    private final Color top;
    private final Color bottom;

    GradientPanel(Color top, Color bottom) {
        this.top = top;
        this.bottom = bottom;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        GradientPaint gp = new GradientPaint(0, 0, top, 0, getHeight(), bottom);
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
        super.paintComponent(g);
    }
}

// ===================== PANEL KARTU DENGAN SUDUT MELENGKUNG =====================
class RoundedPanel extends JPanel {
    private final Color bg;
    private final int radius;

    RoundedPanel(Color bg, int radius) {
        this.bg = bg;
        this.radius = radius;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(bg);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.dispose();
        super.paintComponent(g);
    }
}

// ===================== TEXT FIELD MELENGKUNG DENGAN ANIMASI FOKUS =====================
class RoundedField extends JTextField {
    private final Color focusColor;
    private final Color normalColor;
    private Color currentBorderColor;
    private javax.swing.Timer animTimer;

    RoundedField(Color focusColor, Color bgColor, Color normalBorder, Color textColor) {
        this.focusColor = focusColor;
        this.normalColor = normalBorder;
        this.currentBorderColor = normalBorder;

        setOpaque(false);
        setBackground(bgColor);
        setForeground(textColor);
        setCaretColor(focusColor);
        setFont(new Font("Segoe UI", Font.PLAIN, 15));
        setBorder(BorderFactory.createEmptyBorder(9, 14, 9, 14));

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                animateBorder(focusColor);
            }

            @Override
            public void focusLost(FocusEvent e) {
                animateBorder(normalColor);
            }
        });
    }

    private void animateBorder(final Color target) {
        if (animTimer != null && animTimer.isRunning()) {
            animTimer.stop();
        }
        final Color start = currentBorderColor;
        final int steps = 10;
        final int[] step = {0};

        animTimer = new javax.swing.Timer(15, null);
        animTimer.addActionListener(e -> {
            step[0]++;
            float ratio = (float) step[0] / steps;
            if (ratio > 1f) {
                ratio = 1f;
            }

            int r = (int) (start.getRed() + (target.getRed() - start.getRed()) * ratio);
            int g = (int) (start.getGreen() + (target.getGreen() - start.getGreen()) * ratio);
            int b = (int) (start.getBlue() + (target.getBlue() - start.getBlue()) * ratio);
            currentBorderColor = new Color(r, g, b);
            repaint();

            if (step[0] >= steps) {
                animTimer.stop();
            }
        });
        animTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(currentBorderColor);
        g2.setStroke(new BasicStroke(1.6f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
        g2.dispose();
    }
}

// ===================== TOMBOL GRADIENT DENGAN EFEK HOVER =====================
class GradientButton extends JButton {
    private final Color c1;
    private final Color c2;
    private boolean hover = false;
    private boolean pressedDown = false;

    GradientButton(String text, Color c1, Color c2) {
        super(text);
        this.c1 = c1;
        this.c2 = c2;

        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                pressedDown = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pressedDown = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color start = hover ? c1.brighter() : c1;
        Color end = hover ? c2.brighter() : c2;
        if (pressedDown) {
            start = start.darker();
            end = end.darker();
        }

        GradientPaint gp = new GradientPaint(0, 0, start, getWidth(), getHeight(), end);
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        if (hover) {
            g2.setColor(new Color(255, 255, 255, 60));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 18, 18);
        }

        g2.dispose();
        super.paintComponent(g);
    }
}

// ===================== ICON BADGE VEKTOR (TANPA FONT EMOJI) =====================
class IconBadge extends JComponent {
    enum Type { ID, PERSON, GRAD }

    private final Type type;
    private final Color c1;
    private final Color c2;

    IconBadge(Type type, Color c1, Color c2) {
        this.type = type;
        this.c1 = c1;
        this.c2 = c2;
        setPreferredSize(new Dimension(30, 30));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        GradientPaint gp = new GradientPaint(0, 0, c1, w, h, c2);
        g2.setPaint(gp);
        g2.fillOval(0, 0, w - 1, h - 1);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(1.7f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        int pad = 8;
        if (type == Type.ID) {
            g2.drawRoundRect(pad, pad + 2, w - 2 * pad, h - 2 * pad - 4, 3, 3);
            g2.drawLine(pad + 3, pad + 7, pad + 8, pad + 7);
            g2.drawLine(pad + 3, pad + 11, w - pad - 3, pad + 11);
        } else if (type == Type.PERSON) {
            g2.drawOval(w / 2 - 4, pad, 8, 8);
            g2.drawArc(pad + 1, h / 2, w - 2 * pad - 2, h / 2 - 1, 0, 180);
        } else if (type == Type.GRAD) {
            int[] xs = {w / 2, pad, w - pad};
            int[] ys = {pad, pad + 8, pad + 8};
            g2.drawPolygon(xs, ys, 3);
            g2.drawLine(w / 2, pad + 8, w / 2, h - pad);
        }

        g2.dispose();
    }
}
