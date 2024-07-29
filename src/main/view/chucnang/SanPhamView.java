package main.view.chucnang;

import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import main.entity.Cpu;
import main.entity.Gpu;
import main.entity.Imei;
import main.entity.ManHinh;
import main.entity.OCung;
import main.entity.Pin;
import main.entity.Ram;
import main.repository.CpuRepository;
import main.repository.GpuRepository;
import main.repository.ImeiRepository;
import main.repository.ManHinhRepository;
import main.repository.OCungRepository;
import main.repository.PinRepository;
import main.repository.RamRepository;
import main.repository.SanPhamRepository;
import main.request.FindSanPham;
import main.response.SanPhamResponse;
import main.utils.XImage;
import main.view.sanphamchitiet.CpuView;
import main.view.sanphamchitiet.GpuView;
import main.view.sanphamchitiet.ManHinhView;
import main.view.sanphamchitiet.OCungView;
import main.view.sanphamchitiet.PinView;
import main.view.sanphamchitiet.RamView;

public class SanPhamView extends javax.swing.JInternalFrame {

    JFileChooser fileChooser = new JFileChooser();

    private DefaultTableModel dtmSanPham;
    private DefaultTableModel dtmImei;
    private DefaultTableModel dtmImeiChiTiet;
    private DefaultTableModel dtmSanPhamDaXoa;
    private SanPhamRepository sanphamRepository;

    private DefaultComboBoxModel cpuDcbm;
    private DefaultComboBoxModel gpuDcbm;
    private DefaultComboBoxModel ramDcbm;
    private DefaultComboBoxModel manhinhDcbm;
    private DefaultComboBoxModel pinDcbm;
    private DefaultComboBoxModel ocungDcbm;
    private DefaultComboBoxModel sanphamDcbm;

    private CpuRepository cpuRepository;
    private GpuRepository gpuRepository;
    private RamRepository ramRepository;
    private ManHinhRepository manhinhRepository;
    private PinRepository pinRepository;
    private OCungRepository ocungRepository;
    private ImeiRepository imeiRepository;
    private SanPhamResponse sanphamResponse;

    public SanPhamView() {
        initComponents();
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
        UIManager.put("TabbedPane.tabsOverlapBorder", true);
        this.cauhinhForm();
        dtmSanPham = (DefaultTableModel) tblQuanLySP.getModel();
        dtmImei = (DefaultTableModel) tblQLyImei.getModel();
        dtmImeiChiTiet = (DefaultTableModel) tblImeiCT.getModel();
        dtmSanPhamDaXoa = (DefaultTableModel) tblQuanLySPDaXoa.getModel();
        sanphamRepository = new SanPhamRepository();
        sanphamResponse = new SanPhamResponse();
        this.cboDinhDang();
        this.repositoryDinhDang();
        this.showComboboxCPU();
        this.showComboboxGPU();
        this.showComboboxManHinh();
        this.showComboboxOCung();
        this.showComboboxPin();
        this.showComboboxRam();
        this.showComboboxSanPham();
        this.showDataTableSanPham(sanphamRepository.getAll(getFormSearch()));
        this.showDataTableImei(sanphamRepository.getAll(getFormSearch()));
        this.showDataTableDaXoa(sanphamRepository.getAllDelete());
    }

    public void chonAnh() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XImage.save(file);
            ImageIcon icon = XImage.read(file.getName());
            lbHinhAnh.setIcon(icon);
            lbHinhAnh.setToolTipText(file.getName());
        }
    }

    public void cauhinhForm() {
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
    }

    private void cboDinhDang() {
        cpuDcbm = (DefaultComboBoxModel) cboCPU.getModel();
        gpuDcbm = (DefaultComboBoxModel) cboGPU.getModel();
        ramDcbm = (DefaultComboBoxModel) cboRam.getModel();
        manhinhDcbm = (DefaultComboBoxModel) cboKichThuoc.getModel();
        pinDcbm = (DefaultComboBoxModel) cboPin.getModel();
        ocungDcbm = (DefaultComboBoxModel) cboOCung.getModel();
        sanphamDcbm = (DefaultComboBoxModel) cboMaSP.getModel();
    }

    private void repositoryDinhDang() {
        cpuRepository = new CpuRepository();
        gpuRepository = new GpuRepository();
        ramRepository = new RamRepository();
        manhinhRepository = new ManHinhRepository();
        pinRepository = new PinRepository();
        ocungRepository = new OCungRepository();
        imeiRepository = new ImeiRepository();
    }

    public void showComboboxCPU() {
        cpuDcbm.removeAllElements();
        for (Cpu cpu : cpuRepository.getAll()) {
            cpuDcbm.addElement(cpu.getTenCPU());
        }
    }

    public void showComboboxGPU() {
        gpuDcbm.removeAllElements();
        for (Gpu gpu : gpuRepository.getAll()) {
            gpuDcbm.addElement(gpu.getTenGPU());
        }
    }

    public void showComboboxRam() {
        ramDcbm.removeAllElements();
        for (Ram ram : ramRepository.getAll()) {
            ramDcbm.addElement(ram.getDungLuongRam());
        }
    }

    public void showComboboxManHinh() {
        manhinhDcbm.removeAllElements();
        for (ManHinh mh : manhinhRepository.getAll()) {
            manhinhDcbm.addElement(mh.getKichThuoc());
        }
    }

    public void showComboboxPin() {
        pinDcbm.removeAllElements();
        for (Pin pin : pinRepository.getAll()) {
            pinDcbm.addElement(pin.getDungLuongPin());
        }
    }

    public void showComboboxOCung() {
        ocungDcbm.removeAllElements();
        for (OCung oc : ocungRepository.getAll()) {
            ocungDcbm.addElement(oc.getLoaiOCung());
        }
    }

    public void showComboboxSanPham() {
        sanphamDcbm.removeAllElements();
        for (SanPhamResponse sanPhamResponse : sanphamRepository.getAll(getFormSearch())) {
            sanphamDcbm.addElement(sanPhamResponse.getMaSanPham());
        }
    }

    public void showDataTableSanPham(ArrayList<SanPhamResponse> list) {
        dtmSanPham.setRowCount(0);
        list.forEach(x -> dtmSanPham.addRow(new Object[]{
            x.getMaSanPham(), x.getTenSanPham(), x.getHinhAnh(), x.getTenCPU(), x.getTenGPU(),
            x.getLoaiOCung(), x.getDungLuongRam(), x.getKichThuoc(), x.getDungLuongPin(),
            x.getGiaNhap(), x.getGiaBan()
        }));
    }

    public void showDataTableImei(ArrayList<SanPhamResponse> list) {
        dtmImei.setRowCount(0);
        AtomicInteger index = new AtomicInteger(1);
        list.forEach(x -> dtmImei.addRow(new Object[]{
            index.getAndIncrement(), x.getMaSanPham(), x.getTenSanPham(), x.getGiaNhap(), x.getGiaBan(), x.getSoLuong()
        }));
    }

    public void ShowDataTableImeiChiTiet(String MaSP) {
        dtmImeiChiTiet.setRowCount(0);
        AtomicInteger index = new AtomicInteger(1);
        sanphamRepository.getImeiByMaSP(MaSP).forEach(x -> dtmImeiChiTiet.addRow(new Object[]{
            index.getAndIncrement(), MaSP, x.getMaImei()
        }));
    }

    public void showDataTableDaXoa(ArrayList<SanPhamResponse> list) {
        dtmSanPhamDaXoa.setRowCount(0);
        list.forEach(x -> dtmSanPhamDaXoa.addRow(new Object[]{
            x.getMaSanPham(), x.getTenSanPham(), x.getHinhAnh(), x.getTenCPU(), x.getTenGPU(),
            x.getLoaiOCung(), x.getDungLuongRam(), x.getKichThuoc(), x.getDungLuongPin(), x.getSoLuong(),
            x.getGiaNhap(), x.getGiaBan()
        }));
    }
    private ImageIcon resizeImage(String imagePath, int width, int height) {
        ImageIcon originalIcon = XImage.read(imagePath);
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    private void detail(int index) {
        SanPhamResponse spReponse = sanphamRepository.getAll(getFormSearch()).get(index);
        txtTenSP.setText(spReponse.getTenSanPham());
        txtGiaNhap.setText(String.valueOf(spReponse.getGiaNhap()));
        txtGiaBan.setText(String.valueOf(spReponse.getGiaBan()));
        if (spReponse.getHinhAnh() != null && !spReponse.getHinhAnh().isEmpty()) {
            lbHinhAnh.setIcon(resizeImage(spReponse.getHinhAnh(), 250, 200));
            lbHinhAnh.setToolTipText(spReponse.getHinhAnh());
        } else {
            lbHinhAnh.setIcon(null);
            lbHinhAnh.setToolTipText(null);
        }
        cboCPU.setSelectedItem(spReponse.getTenCPU());
        cboGPU.setSelectedItem(spReponse.getTenGPU());
        cboOCung.setSelectedItem(spReponse.getLoaiOCung());
        cboRam.setSelectedItem(spReponse.getDungLuongRam());
        cboKichThuoc.setSelectedItem(spReponse.getKichThuoc());
        cboPin.setSelectedItem(spReponse.getDungLuongPin());
    }
    
    private void detailImeiCT(int index){
        SanPhamResponse imeiSp = sanphamRepository.getImeiByMaSP(tblImeiCT.getValueAt(index, 1).toString()).get(index);
        txtImeiCT.setText(imeiSp.getMaImei());
    }

    private SanPhamResponse getFormDataSanPham() {
        int indexCPU = cboCPU.getSelectedIndex();
        int indexGPU = cboGPU.getSelectedIndex();
        int indexRam = cboRam.getSelectedIndex();
        int indexManHinh = cboKichThuoc.getSelectedIndex();
        int indexOCung = cboOCung.getSelectedIndex();
        int indexPin = cboPin.getSelectedIndex();
        return SanPhamResponse.builder()
                .IdRam(ramRepository.getAll().get(indexRam).getIdRam())
                .IdCPU(cpuRepository.getAll().get(indexCPU).getIdCPU())
                .IdGPU(gpuRepository.getAll().get(indexGPU).getIdGPU())
                .IdManHinh(manhinhRepository.getAll().get(indexManHinh).getIdManHinh())
                .IdOCung(ocungRepository.getAll().get(indexOCung).getIdOCung())
                .IdPin(pinRepository.getAll().get(indexPin).getIdPin())
                .TenSanPham(txtTenSP.getText())
                .HinhAnh(lbHinhAnh.getToolTipText())
                .GiaNhap(Integer.parseInt(txtGiaNhap.getText()))
                .GiaBan(Integer.parseInt(txtGiaBan.getText()))
                .build();
    }

    private Imei getFormDataImei() {
        return Imei.builder()
                .MaImei(txtMaImei.getText())
                .build();
    }

    private FindSanPham getFormSearch() {
        FindSanPham fsp = new FindSanPham();
        fsp.setKeySearch1(txtSearch.getText());
        fsp.setKeySearchGiaMin(txtGiaMin.getText());
        fsp.setKeySearchGiaMax(txtGiaMax.getText());
        return fsp;
    }

    private int getIdSpByMa() {
        for (SanPhamResponse spr : sanphamRepository.getAll(getFormSearch())) {
            if (cboMaSP.getSelectedItem().equals(spr.getMaSanPham())) {
                return spr.getIdSanPham();
            }
        }
        return 0;
    }

    private int getSanPhamSelectedRow() {
        for (int i = 0; i < dtmImei.getRowCount(); i++) {
            if (dtmImei.getValueAt(i, 1).equals(sanphamDcbm.getSelectedItem())) {
                return i;
            }
        }
        return -1;
    }
    
    private int getImeiSelectedRow(){
        for(int i = 0; i < dtmImeiChiTiet.getRowCount(); i++){
            if(dtmImeiChiTiet.getValueAt(i, 1).equals(sanphamResponse.getMaSanPham())){
                return i;
            }
        }
        return -1;
    }

    private int getSoLuong() {
        int soLuong = sanphamRepository.getAll(getFormSearch()).get(this.getSanPhamSelectedRow()).getSoLuong();
        return soLuong;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDayChooser1 = new com.toedter.calendar.JDayChooser();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbHinhAnh = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtGiaNhap = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtGiaBan = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        cboCPU = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        btnAddCPU = new javax.swing.JButton();
        cboRam = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        btnAddRam = new javax.swing.JButton();
        cboGPU = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        btnAddGPU = new javax.swing.JButton();
        cboPin = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        btnAddPin = new javax.swing.JButton();
        cboOCung = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        btnAddOCung = new javax.swing.JButton();
        cboKichThuoc = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        btnAddKichThuoc = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtGiaMin = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtGiaMax = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblQuanLySP = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblQLyImei = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cboMaSP = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtMaImei = new javax.swing.JTextField();
        btnAddImei = new javax.swing.JButton();
        btnClearImei = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblImeiCT = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtImeiCT = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblQuanLySPDaXoa = new javax.swing.JTable();
        btnRestore = new javax.swing.JButton();

        setBorder(null);
        setForeground(java.awt.Color.lightGray);
        setMaximizable(true);
        setFrameIcon(null);

        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " ", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        lbHinhAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbHinhAnh.setText("Chọn hình ảnh");
        lbHinhAnh.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        lbHinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbHinhAnhMousePressed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel16.setText("Tên SP");

        txtTenSP.setMinimumSize(new java.awt.Dimension(64, 30));
        txtTenSP.setPreferredSize(new java.awt.Dimension(64, 30));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel19.setText("Giá nhập");

        txtGiaNhap.setMinimumSize(new java.awt.Dimension(64, 30));
        txtGiaNhap.setPreferredSize(new java.awt.Dimension(64, 30));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel20.setText("Giá bán");

        txtGiaBan.setMinimumSize(new java.awt.Dimension(64, 30));
        txtGiaBan.setPreferredSize(new java.awt.Dimension(64, 30));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel16)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtGiaBan, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                            .addComponent(txtGiaNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(52, 52, 52)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thuộc Tính", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 15))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.add(cboCPU, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 140, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("CPU");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 40, -1));

        btnAddCPU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/icon/32378_add_plus_icon.png"))); // NOI18N
        btnAddCPU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCPUActionPerformed(evt);
            }
        });
        jPanel3.add(btnAddCPU, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 140, 30));

        jPanel3.add(cboRam, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, 140, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("RAM");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, -1, -1));

        btnAddRam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/icon/32378_add_plus_icon.png"))); // NOI18N
        btnAddRam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddRamActionPerformed(evt);
            }
        });
        jPanel3.add(btnAddRam, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, 140, 30));

        jPanel3.add(cboGPU, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 140, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("GPU");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        btnAddGPU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/icon/32378_add_plus_icon.png"))); // NOI18N
        btnAddGPU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddGPUActionPerformed(evt);
            }
        });
        jPanel3.add(btnAddGPU, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 140, 30));

        jPanel3.add(cboPin, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, 140, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("PIN");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 40, -1, -1));

        btnAddPin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/icon/32378_add_plus_icon.png"))); // NOI18N
        btnAddPin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPinActionPerformed(evt);
            }
        });
        jPanel3.add(btnAddPin, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 90, 140, 30));

        jPanel3.add(cboOCung, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 150, 140, 30));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Ổ CỨNG");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, -1, -1));

        btnAddOCung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/icon/32378_add_plus_icon.png"))); // NOI18N
        btnAddOCung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddOCungActionPerformed(evt);
            }
        });
        jPanel3.add(btnAddOCung, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 180, 140, 30));

        jPanel3.add(cboKichThuoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 150, 140, 30));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("KÍCH THƯỚC");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 130, -1, -1));

        btnAddKichThuoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/icon/32378_add_plus_icon.png"))); // NOI18N
        btnAddKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddKichThuocActionPerformed(evt);
            }
        });
        jPanel3.add(btnAddKichThuoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 180, 140, 30));

        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/icon/32378_add_plus_icon.png"))); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel3.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 70, 100, 50));

        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/icon/1582587_arrow_refresh_reload_rotate icon_icon.png"))); // NOI18N
        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel3.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 70, 100, 50));

        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/icon/11211468_eraser_clear_remove_tool_rubber_icon.png"))); // NOI18N
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        jPanel3.add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 150, 100, 50));

        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/icon/5049209_bin_delete_remove_trash_icon.png"))); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel3.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 150, 100, 50));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh Sách Sản Phẩm", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Tìm kiếm");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Lọc theo giá bán");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel4.setText("->");

        tblQuanLySP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Tên SP", "Hình ảnh", "CPU", "GPU", "Loại ổ cứng", "Ram", "Kích thước", "Pin", "Giá nhập", "Giá bán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblQuanLySP.getTableHeader().setReorderingAllowed(false);
        tblQuanLySP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQuanLySPMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblQuanLySP);
        if (tblQuanLySP.getColumnModel().getColumnCount() > 0) {
            tblQuanLySP.getColumnModel().getColumn(0).setResizable(false);
            tblQuanLySP.getColumnModel().getColumn(0).setPreferredWidth(70);
            tblQuanLySP.getColumnModel().getColumn(1).setMinWidth(130);
            tblQuanLySP.getColumnModel().getColumn(3).setPreferredWidth(80);
            tblQuanLySP.getColumnModel().getColumn(9).setResizable(false);
            tblQuanLySP.getColumnModel().getColumn(10).setResizable(false);
        }

        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtGiaMin, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtGiaMax, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jButton2)
                        .addGap(26, 26, 26))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtGiaMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtGiaMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Thông Tin Sản Phẩm", jPanel4);

        tblQLyImei.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã SP", "Tên SP", "Giá Nhập", "Giá Bán", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblQLyImei.getTableHeader().setReorderingAllowed(false);
        tblQLyImei.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQLyImeiMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblQLyImeiMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblQLyImei);
        if (tblQLyImei.getColumnModel().getColumnCount() > 0) {
            tblQLyImei.getColumnModel().getColumn(0).setPreferredWidth(20);
            tblQLyImei.getColumnModel().getColumn(2).setPreferredWidth(100);
        }

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("THÊM IMEI CHO SẢN PHẨM");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Mã Sản Phẩm");

        cboMaSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Mã IMEI");

        btnAddImei.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/icon/32378_add_plus_icon.png"))); // NOI18N
        btnAddImei.setText("Thêm Imei");
        btnAddImei.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddImeiActionPerformed(evt);
            }
        });

        btnClearImei.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/icon/11211468_eraser_clear_remove_tool_rubber_icon.png"))); // NOI18N
        btnClearImei.setText("Làm mới");
        btnClearImei.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearImeiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(98, 98, 98))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaImei)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(cboMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(btnAddImei)
                .addGap(56, 56, 56)
                .addComponent(btnClearImei)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaImei, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddImei, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClearImei, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(108, Short.MAX_VALUE))
        );

        tblImeiCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã SP", "Imei"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblImeiCT.getTableHeader().setReorderingAllowed(false);
        tblImeiCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblImeiCTMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblImeiCT);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Imei Sản Phẩm Chi Tiết");

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setText("IMEI CHI TIẾT");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/icon/5049209_bin_delete_remove_trash_icon.png"))); // NOI18N
        jButton1.setText("Xóa");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(156, 156, 156))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(txtImeiCT, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(181, 181, 181)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(txtImeiCT, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 38, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE))
                .addContainerGap(121, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Imei Sản Phẩm", jPanel5);

        tblQuanLySPDaXoa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Tên SP", "Hình ảnh", "CPU", "GPU", "Loại ổ cứng", "Ram", "Kích thước", "Pin", "Số lượng", "Giá nhập", "Giá bán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblQuanLySPDaXoa.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tblQuanLySPDaXoa);
        if (tblQuanLySPDaXoa.getColumnModel().getColumnCount() > 0) {
            tblQuanLySPDaXoa.getColumnModel().getColumn(0).setResizable(false);
            tblQuanLySPDaXoa.getColumnModel().getColumn(0).setPreferredWidth(70);
            tblQuanLySPDaXoa.getColumnModel().getColumn(1).setMinWidth(130);
            tblQuanLySPDaXoa.getColumnModel().getColumn(3).setPreferredWidth(80);
            tblQuanLySPDaXoa.getColumnModel().getColumn(10).setResizable(false);
            tblQuanLySPDaXoa.getColumnModel().getColumn(11).setResizable(false);
        }

        btnRestore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/icon/1582587_arrow_refresh_reload_rotate icon_icon.png"))); // NOI18N
        btnRestore.setText("Khôi phục");
        btnRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestoreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1039, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRestore))
                .addGap(0, 13, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(btnRestore, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(390, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sản Phẩm Đã Xóa", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jTabbedPane1)
                .addGap(74, 74, 74))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddCPUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCPUActionPerformed
        CpuView cpu = new CpuView(this);
        cpu.setVisible(true);
    }//GEN-LAST:event_btnAddCPUActionPerformed

    private void btnAddGPUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddGPUActionPerformed
        GpuView gpu = new GpuView(this);
        gpu.setVisible(true);
    }//GEN-LAST:event_btnAddGPUActionPerformed

    private void btnAddRamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddRamActionPerformed
        RamView ram = new RamView(this);
        ram.setVisible(true);
    }//GEN-LAST:event_btnAddRamActionPerformed

    private void btnAddKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddKichThuocActionPerformed
        ManHinhView mh = new ManHinhView(this);
        mh.setVisible(true);
    }//GEN-LAST:event_btnAddKichThuocActionPerformed

    private void btnAddPinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPinActionPerformed
        PinView p = new PinView(this);
        p.setVisible(true);
    }//GEN-LAST:event_btnAddPinActionPerformed

    private void tblQuanLySPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQuanLySPMouseClicked
        this.detail(tblQuanLySP.getSelectedRow());
    }//GEN-LAST:event_tblQuanLySPMouseClicked

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        lbHinhAnh.setIcon(null);
        txtTenSP.setText("");
        txtGiaBan.setText("");
        txtGiaNhap.setText("");
        cboCPU.setSelectedItem("");
        cboGPU.setSelectedItem("");
        cboKichThuoc.setSelectedItem("");
        cboPin.setSelectedItem("");
        cboOCung.setSelectedItem("");
        cboRam.setSelectedItem("");
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        String giaNhap = txtGiaNhap.getText();
        String giaBan = txtGiaBan.getText();
        if (txtTenSP.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên SP không dc để trống");
            txtTenSP.requestFocus();
            return;
        }
        if (giaNhap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giá nhập không dc để trống");
            txtGiaNhap.requestFocus();
            return;
        }
        if (giaBan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giá bán không dc để trống");
            txtGiaBan.requestFocus();
            return;
        }
        try {
            Integer GiaNhap = Integer.parseInt(giaNhap);
            Integer GiaBan = Integer.parseInt(giaBan);
            if (GiaBan < GiaNhap) {
                JOptionPane.showMessageDialog(this, "Giá bán không thể nhỏ hơn giá nhập !");
                txtGiaBan.requestFocus();
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ");
        }
        int chon = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn thêm sản phẩm này chưa ?");
        if (chon == 0) {
            if (sanphamRepository.add(this.getFormDataSanPham())) {
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                this.showDataTableSanPham(sanphamRepository.getAll(getFormSearch()));
                this.showDataTableImei(sanphamRepository.getAll(getFormSearch()));
                this.showComboboxSanPham();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnAddImeiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddImeiActionPerformed
        if (txtMaImei.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã Imei không được để trống");
            txtMaImei.requestFocus();
            return;
        }
        for (Imei imei : imeiRepository.getAll()) {
            if (txtMaImei.getText().equals(imei.getMaImei())) {
                JOptionPane.showMessageDialog(this, "Mã Imei này đã tồn tại !");
                txtMaImei.requestFocus();
                return;
            }
        }
        int chon = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn thêm Imei này chưa ?");
        if (chon == 0) {
            if (imeiRepository.add(this.getFormDataImei(), this.getIdSpByMa())) {
                JOptionPane.showMessageDialog(this, "Thêm thành công !");
                this.ShowDataTableImeiChiTiet(sanphamResponse.getMaSanPham());
                sanphamRepository.updateQuantity(this.getSoLuong() + 1, sanphamRepository.getAll(getFormSearch()).get(this.getSanPhamSelectedRow()).getMaSanPham());
                this.showDataTableImei(sanphamRepository.getAll(getFormSearch()));
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        }

    }//GEN-LAST:event_btnAddImeiActionPerformed

    private void btnClearImeiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearImeiActionPerformed
        txtMaImei.setText("");
    }//GEN-LAST:event_btnClearImeiActionPerformed

    private void tblQLyImeiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQLyImeiMouseClicked

    }//GEN-LAST:event_tblQLyImeiMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int index = tblQuanLySP.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn Laptop muốn xóa");
        } else {
            int chon = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa sản phẩm này chưa ?");
            if (chon == 0) {
                if (sanphamRepository.delete(sanphamRepository.getAll(getFormSearch()).get(index).getIdSanPham())) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công !");
                    txtTenSP.setText("");
                    txtGiaBan.setText("");
                    txtGiaNhap.setText("");
                    this.showDataTableSanPham(sanphamRepository.getAll(getFormSearch()));
                    this.showDataTableImei(sanphamRepository.getAll(getFormSearch()));
                    this.showComboboxSanPham();
                    this.showDataTableDaXoa(sanphamRepository.getAllDelete());
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại");
                }
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int index = tblQuanLySP.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn Laptop muốn sửa");
        } else {
            int chon = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn sửa sản phẩm này chưa ?");
            if (chon == 0) {
                if (sanphamRepository.update(this.getFormDataSanPham(), sanphamRepository.getAll(getFormSearch()).get(index).getIdSanPham())) {
                    JOptionPane.showMessageDialog(this, "Sửa thành công !");
                    this.showDataTableSanPham(sanphamRepository.getAll(getFormSearch()));
                } else {
                    JOptionPane.showMessageDialog(this, "Sửa thất bại");
                }
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.showDataTableSanPham(sanphamRepository.getAll(getFormSearch()));
    }//GEN-LAST:event_jButton2ActionPerformed

    private void lbHinhAnhMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbHinhAnhMousePressed
        if (evt.getClickCount() == 2) {
            chonAnh();
        }
    }//GEN-LAST:event_lbHinhAnhMousePressed

    private void tblQLyImeiMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQLyImeiMousePressed
        int index = tblQLyImei.getSelectedRow();
        this.ShowDataTableImeiChiTiet(sanphamRepository.getAll(getFormSearch()).get(index).getMaSanPham());
    }//GEN-LAST:event_tblQLyImeiMousePressed

    private void btnRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestoreActionPerformed
        int index = tblQuanLySPDaXoa.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm muốn khôi phục");
        } else {
            if (sanphamRepository.restore(sanphamRepository.getAllDelete().get(index).getIdSanPham())) {
                JOptionPane.showMessageDialog(this, "Khôi phục sản phẩm thành công !");
                this.showDataTableDaXoa(sanphamRepository.getAllDelete());                
                this.showDataTableImei(sanphamRepository.getAll(getFormSearch()));
                this.showDataTableSanPham(sanphamRepository.getAll(getFormSearch()));
            } else {
                JOptionPane.showMessageDialog(this, "Khôi phục thất bại");
            }
        }
    }//GEN-LAST:event_btnRestoreActionPerformed

    private void tblImeiCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblImeiCTMouseClicked
        int index = tblImeiCT.getSelectedRow();
        this.detailImeiCT(index);
    }//GEN-LAST:event_tblImeiCTMouseClicked

    private void btnAddOCungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddOCungActionPerformed
        OCungView ocung = new OCungView(this);
        ocung.setVisible(true);
    }//GEN-LAST:event_btnAddOCungActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(txtImeiCT.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Vui lòng chọn imei cần xóa");
        }else{
            if(imeiRepository.delete(txtImeiCT.getText())){
                JOptionPane.showMessageDialog(this, "Xóa thành công !");
                this.ShowDataTableImeiChiTiet(tblImeiCT.getValueAt(this.getSanPhamSelectedRow(), 1).toString());
                sanphamRepository.updateQuantity(this.getSoLuong() - 1, sanphamRepository.getAll(getFormSearch()).get(this.getImeiSelectedRow()).getMaSanPham());
                this.showDataTableImei(sanphamRepository.getAll(getFormSearch()));
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddCPU;
    private javax.swing.JButton btnAddGPU;
    private javax.swing.JButton btnAddImei;
    private javax.swing.JButton btnAddKichThuoc;
    private javax.swing.JButton btnAddOCung;
    private javax.swing.JButton btnAddPin;
    private javax.swing.JButton btnAddRam;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClearImei;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRestore;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboCPU;
    private javax.swing.JComboBox<String> cboGPU;
    private javax.swing.JComboBox<String> cboKichThuoc;
    private javax.swing.JComboBox<String> cboMaSP;
    private javax.swing.JComboBox<String> cboOCung;
    private javax.swing.JComboBox<String> cboPin;
    private javax.swing.JComboBox<String> cboRam;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private com.toedter.calendar.JDayChooser jDayChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbHinhAnh;
    private javax.swing.JTable tblImeiCT;
    private javax.swing.JTable tblQLyImei;
    private javax.swing.JTable tblQuanLySP;
    private javax.swing.JTable tblQuanLySPDaXoa;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaMax;
    private javax.swing.JTextField txtGiaMin;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtImeiCT;
    private javax.swing.JTextField txtMaImei;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTenSP;
    // End of variables declaration//GEN-END:variables
}
