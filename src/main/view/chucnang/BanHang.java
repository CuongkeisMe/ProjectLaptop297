package main.view.chucnang;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import main.entity.HoaDonTro;
import main.response.BanHangResponse;
import main.entity.KhachHang;
import main.repository.BanHangSPRepositories;
import main.repository.HoaDonChiTietRepository;
import main.repository.HoaDonRepository;
import main.repository.KhachHangRepository;
import main.request.FindKhachHang;
import main.request.FindSanPham;
import main.response.HoaDonChiTietResponse;
import main.view.sanphamchitiet.BHChonKH;
import main.view.sanphamchitiet.ImeiChiTiet;

public class BanHang extends javax.swing.JInternalFrame {

    private DefaultTableModel dtmSP;
    private DefaultTableModel dfhoadon;
    private DefaultTableModel dtmGioHang;
    private BanHangSPRepositories banhangRepository;
    private HoaDonChiTietRepository hdctRepository;
    private HoaDonRepository hdsp;
    private BanHangResponse banHangResponse;
    private HoaDonTro hoaDonTro;

    DecimalFormat decimalFormat = new DecimalFormat("#,##0");

    public BanHang() {
        initComponents();
        this.cauhinhForm();
        dtmSP = (DefaultTableModel) tblSP.getModel();
        dtmGioHang = (DefaultTableModel) tblGioHang.getModel();
        dfhoadon = (DefaultTableModel) tblHoaDonTro.getModel();
        banhangRepository = new BanHangSPRepositories();
        hdctRepository = new HoaDonChiTietRepository();
        hdsp = new HoaDonRepository();
        hoaDonTro = new HoaDonTro();
        banHangResponse = new BanHangResponse();
        this.showDataTableSP(banhangRepository.getAll(getFormSearch()));
        this.showDatahoadon(hdsp.getAllHoaDon());

    }

    public void showDataTableSP(ArrayList<BanHangResponse> list) {
        dtmSP.setRowCount(0);
        list.forEach(x -> dtmSP.addRow(new Object[]{
            x.getMaSanPham(), x.getTenSanPham(), x.getTenCPU(), x.getTenGPU(), x.getLoaiOCung(), x.getDungluongRam(),
            x.getKichThuoc(), x.getDungluongPin(), decimalFormat.format(x.getGiaBan()), banhangRepository.getSoLuongByMa(x.getMaSanPham())
        }));
    }

    public void showDataGioHang() {
        dtmGioHang.setRowCount(0);
        AtomicInteger index = new AtomicInteger(1);
        hdctRepository.getAll(this.getIdHoaDonByMa(layMaHD())).forEach(x -> dtmGioHang.addRow(new Object[]{
            index.getAndIncrement(), x.getMaSanPham(), x.getTenSanPham(), decimalFormat.format(x.getGiaBan()), x.getSoLuong(), decimalFormat.format(x.getTongTien())
        }));
    }

    public void showDatahoadon(ArrayList<HoaDonTro> list) {
        dfhoadon.setRowCount(0);
        for (int i = 0; i < list.size(); i++) {
            HoaDonTro hd = list.get(i);
            dfhoadon.addRow(new Object[]{
                i + 1,
                hd.getMaHoaDon(),
                hd.getNgayTao(),
                hd.getMaNhanVien(),
                hd.getTinhTrang() == 1 ? "Đã Thanh Toán" : "Chưa Thanh Toán"});
        }
    }

    public FindSanPham getFormSearch() {
        FindSanPham fsp = new FindSanPham();
        fsp.setKeySearch1(txtSearch.getText());
        return fsp;
    }
    
    public void cauhinhForm() {
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
    }

    public String layMaSPSelect() {
        int index = tblSP.getSelectedRow();
        return tblSP.getValueAt(index, 0).toString();
    }

    public int getIdByMa(String maSP) {
        for (BanHangResponse sanPham : banhangRepository.getAll(getFormSearch())) {
            if (sanPham.getMaSanPham().equals(maSP)) {
                return sanPham.getIdSanPham();
            }
        }
        return -1;
    }

    public int getIdHoaDonByMa(String maHoaDon) {
        for (int i = 0; i < tblHoaDonTro.getRowCount(); i++) {
            if (tblHoaDonTro.getValueAt(i, 1).equals(maHoaDon)) {
                for (HoaDonTro hoadon : hdsp.getAllHoaDon()) {
                    if (hoadon.getMaHoaDon().equals(maHoaDon)) {
                        return hoadon.getIdHoaDon();
                    }
                }
            }
        }
        return -1;
    }

    public String layMaHD() {
        int index = tblHoaDonTro.getSelectedRow();
        String maHD = tblHoaDonTro.getValueAt(index, 1).toString();
        return maHD;
    }
    
    public int getSoLuong(){
        int index = tblSP.getSelectedRow();
        int soLuong = (int) tblSP.getValueAt(index, 9);
        return soLuong;
    }

    public void addGioHang(float giaBan, Integer idSP, Integer soLuong) {
        banHangResponse = new BanHangResponse();
        banHangResponse.setIdSanPham(idSP);
        banHangResponse.setGiaBan(giaBan);
        if (banhangRepository.addGioHang(this.getIdHoaDonByMa(layMaHD()), banHangResponse, soLuong)) {
            JOptionPane.showMessageDialog(this, "Sản phẩm đã được thêm vào giỏ hàng thành công !");
        } else {
            JOptionPane.showMessageDialog(this, "lỗi khi thêm sản phẩm vào giỏ hàng !");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSearch = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSP = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDonTro = new javax.swing.JTable();
        btnTaoHoaDon = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        btnXoa = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSDTKH = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNameKH = new javax.swing.JTextField();
        btnChon = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();

        btnSearch.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm sản phẩm"));

        tblSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Tên", "CPU", "GPU", "Ổ cứng", "Ram", "Màn hình", "Pin", "Giá", "Số lượng"
            }
        ));
        tblSP.getTableHeader().setReorderingAllowed(false);
        tblSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSPMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblSP);
        if (tblSP.getColumnModel().getColumnCount() > 0) {
            tblSP.getColumnModel().getColumn(1).setMinWidth(130);
        }

        jLabel19.setText("Tìm kiếm");

        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout btnSearchLayout = new javax.swing.GroupLayout(btnSearch);
        btnSearch.setLayout(btnSearchLayout);
        btnSearchLayout.setHorizontalGroup(
            btnSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(btnSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(246, 246, 246)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnSearchLayout.setVerticalGroup(
            btnSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnSearchLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(btnSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Hóa đơn"));

        tblHoaDonTro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã HĐ", "Ngày tạo", "Tên NV", "Tình trạng"
            }
        ));
        tblHoaDonTro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonTroMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblHoaDonTro);

        btnTaoHoaDon.setText("Tạo hóa đơn");
        btnTaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnTaoHoaDon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(38, 38, 38))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(324, 324, 324))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Giỏ hàng"));

        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã SP", "Tên SP", "Đơn giá", "Số lượng", "Thành tiền"
            }
        ));
        jScrollPane3.setViewportView(tblGioHang);
        if (tblGioHang.getColumnModel().getColumnCount() > 0) {
            tblGioHang.getColumnModel().getColumn(0).setPreferredWidth(20);
            tblGioHang.getColumnModel().getColumn(1).setPreferredWidth(70);
            tblGioHang.getColumnModel().getColumn(2).setPreferredWidth(140);
        }

        btnXoa.setText("Xóa SP");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jButton4.setText("Làm mới");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnXoa)
                        .addGap(42, 42, 42)
                        .addComponent(jButton4)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoa)
                    .addComponent(jButton4))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Đơn Hàng"));

        jLabel11.setText("Mã HĐ");

        jLabel12.setText("Tên NV");

        jLabel13.setText("Mã Voucher");

        jTextField10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField10ActionPerformed(evt);
            }
        });

        jLabel14.setText("Giảm giá");

        jLabel15.setText("Tổng tiền");

        jLabel16.setText("Hình thức TT");

        jLabel17.setText("Tiền KH trả");

        jLabel18.setText("Tiền cần trả lại");

        jButton6.setText("Thanh toán");

        jButton7.setText("Làm mới");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin khách hàng:")));

        jLabel1.setText("SĐT");

        jLabel2.setText("Tên KH");

        btnChon.setText("Chọn");
        btnChon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnChonMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSDTKH, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                    .addComponent(txtNameKH))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnChon)
                .addGap(75, 75, 75))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSDTKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNameKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(btnChon))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6)
                .addGap(60, 60, 60)
                .addComponent(jButton7)
                .addGap(19, 19, 19))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel15)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11))
                .addGap(13, 13, 13)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 15, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField9, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField10)
                            .addComponent(jTextField11, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField12, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField14, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField15, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addComponent(jLabel12))
                    .addComponent(jTextField10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton6))
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(108, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void tblSPMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPMousePressed
        int indexHD = tblHoaDonTro.getSelectedRow();

        if (indexHD == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn !");
        } else {
            int indexSP = tblSP.getSelectedRow();
            layMaSPSelect();
            String maSP = (String) tblSP.getValueAt(indexSP, 0);
            if (evt.getClickCount() == 2) {
                ImeiChiTiet imei = new ImeiChiTiet(maSP, this);
                imei.setVisible(true);
            }
        }
    }//GEN-LAST:event_tblSPMousePressed

    private void btnTaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonActionPerformed
        hdsp.themhoatro();
        showDatahoadon(hdsp.getAllHoaDon());
    }//GEN-LAST:event_btnTaoHoaDonActionPerformed

    private void tblSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPMouseClicked

    }//GEN-LAST:event_tblSPMouseClicked
    private void btnChonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChonMousePressed
        // TODO add your handling code here:
        KhachHangRepository repository = new KhachHangRepository();
        FindKhachHang fkh = new FindKhachHang();
        fkh.setKeySearch("");
        ArrayList<KhachHang> customers = repository.getAll(fkh);
        BHChonKH dialog = new BHChonKH();
        dialog.updateTable(customers);
        // Thêm WindowListener để xử lý khi cửa sổ được đóng
        dialog.setVisible(true);
        // Lấy thông tin khách hàng đã chọn
        KhachHang selectedCustomer = dialog.getSelectedCustomer();

    }//GEN-LAST:event_btnChonMousePressed

    private void tblHoaDonTroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonTroMouseClicked
        this.showDataGioHang();
    }//GEN-LAST:event_tblHoaDonTroMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.showDataTableSP(banhangRepository.getAll(getFormSearch()));
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        
    }//GEN-LAST:event_btnXoaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChon;
    private javax.swing.JPanel btnSearch;
    private javax.swing.JButton btnTaoHoaDon;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblHoaDonTro;
    private javax.swing.JTable tblSP;
    private javax.swing.JTextField txtNameKH;
    private javax.swing.JTextField txtSDTKH;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
