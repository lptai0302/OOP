import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DanhSachSanPham {
    private ArrayList<SanPhamThoiTrang> danhSach;

    public DanhSachSanPham() {
        this.danhSach = new ArrayList<>();
    }

    public DanhSachSanPham(ArrayList<SanPhamThoiTrang> danhSach) {
        this.danhSach = (danhSach != null) ? danhSach : new ArrayList<>();
    }

    public ArrayList<SanPhamThoiTrang> getDanhSach() {
        return danhSach;
    }

    public int getSoLuong() {
        return danhSach.size();
    }

    public boolean isEmpty() {
        return danhSach.isEmpty();
    }

    public String taoMaTuDong(String type) {
        String prefix = "SP";
        if (type != null) {
            String t = type.toLowerCase();
            if (t.equals("aokhoac")) {
                prefix = "AK";
            } else if (t.equals("quanjean") || t.equals("jeans")) {
                prefix = "QJ";
            } else if (t.equals("aothun") || t.equals("tshirt")) {
                prefix = "AT";
            } else if (t.equals("phukien") || t.equals("accessory")) {
                prefix = "PK";
            } else if (t.equals("vaydam")) {
                prefix = "VD";
            } else if (t.equals("giaydep")) {
                prefix = "GD";
            }
        }

        int maxId = 0;
        for (SanPhamThoiTrang p : danhSach) {
            if (p.getMaSP() != null && p.getMaSP().toUpperCase().startsWith(prefix)) {
                String numPart = p.getMaSP().substring(prefix.length());
                try {
                    int id = Integer.parseInt(numPart);
                    if (id > maxId) {
                        maxId = id;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return String.format("%s%03d", prefix, maxId + 1);
    }

    public boolean them(SanPhamThoiTrang sp) {
        if (sp == null || sp.getMaSP() == null || sp.getMaSP().trim().isEmpty()) {
            System.out.println("Loi: San pham khong hop le!");
            return false;
        }
        if (timKiem(sp.getMaSP()) != null) {
            System.out.println("San pham co ma " + sp.getMaSP() + " da ton tai!");
            return false;
        }
        danhSach.add(sp);
        return true;
    }

    public boolean xoa(String maSP) {
        if (maSP == null) return false;
        for (int i = 0; i < danhSach.size(); i++) {
            if (danhSach.get(i).getMaSP() != null && danhSach.get(i).getMaSP().equalsIgnoreCase(maSP.trim())) {
                danhSach.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean sua(String maSP, String tenMoi, double giaMoi) {
        SanPhamThoiTrang sp = timKiem(maSP);
        if (sp == null) {
            return false;
        }
        if (tenMoi != null && !tenMoi.trim().isEmpty()) {
            sp.setTenSP(tenMoi.trim());
        }
        if (giaMoi >= 0) {
            sp.setGiaBan(giaMoi);
        }
        return true;
    }

    public SanPhamThoiTrang timKiem(String maSP) {
        if (maSP == null) return null;
        for (SanPhamThoiTrang sp : danhSach) {
            if (sp.getMaSP() != null && sp.getMaSP().equalsIgnoreCase(maSP.trim())) {
                return sp;
            }
        }
        return null;
    }

    public ArrayList<SanPhamThoiTrang> timKiemTheoTen(String tuKhoa) {
        ArrayList<SanPhamThoiTrang> ketQua = new ArrayList<>();
        if (tuKhoa == null || tuKhoa.trim().isEmpty()) return ketQua;
        String kw = tuKhoa.trim().toLowerCase();
        for (SanPhamThoiTrang sp : danhSach) {
            if (sp.getTenSP() != null && sp.getTenSP().toLowerCase().contains(kw)) {
                ketQua.add(sp);
            }
        }
        return ketQua;
    }

    public void hienThiDanhSach() {
        if (danhSach.isEmpty()) {
            System.out.println("Danh sach san pham hien dang trong.");
            return;
        }
        System.out.println("\n=== DANH SACH SAN PHAM TRONG KHO ===");
        SanPhamThoiTrang.inTieuDeBang();
        for (SanPhamThoiTrang sp : danhSach) {
            sp.inDongBang();
        }
        SanPhamThoiTrang.inChanBang();
        System.out.println("Tong so san pham trong kho: " + danhSach.size() + "\n");
    }

    public void docTuFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 5) {
                    System.out.println("Canh bao: Bo qua dong khong hop le " + lineNumber + ": " + line);
                    continue;
                }
                String type = parts[0].trim();
                String code = parts[1].trim();
                String name = parts[2].trim();
                double price;
                try {
                    price = Double.parseDouble(parts[3].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Canh bao: Gia khong hop le o dong " + lineNumber + ". Bo qua.");
                    continue;
                }

                switch (type.toLowerCase()) {
                    case "aokhoac":
                        boolean haveHat = Boolean.parseBoolean(parts[4].trim());
                        them(new AoKhoac("AoKhoac", code, name, price, haveHat));
                        break;
                    case "quanjean":
                    case "jeans":
                        String style = parts[4].trim();
                        them(new QuanJean("QuanJean", code, name, price, style));
                        break;
                    case "aothun":
                    case "tshirt":
                        String size = parts[4].trim();
                        them(new AoThun("AoThun", code, name, price, size));
                        break;
                    case "phukien":
                    case "accessory":
                        String material = parts[4].trim();
                        them(new PhuKien("PhuKien", code, name, price, material));
                        break;
                    case "vaydam":
                        String chatLieu = parts[4].trim();
                        them(new VayDam("VayDam", code, name, price, chatLieu));
                        break;
                    case "giaydep":
                        int szGiay = 40;
                        try {
                            szGiay = Integer.parseInt(parts[4].trim());
                        } catch (NumberFormatException ignored) {}
                        them(new GiayDep("GiayDep", code, name, price, szGiay));
                        break;
                    default:
                        System.out.println("Loai san pham khong xac dinh: " + type);
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Da xay ra loi khi doc du lieu tu file.");
        }
    }

    public void ghiVaoFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
            for (SanPhamThoiTrang sp : danhSach) {
                writer.write(sp.getStringSaveFile());
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println("Da xay ra loi khi luu du lieu vao file.");
        }
    }
}
