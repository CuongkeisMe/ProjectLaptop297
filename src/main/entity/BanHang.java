/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BanHang {
    private String maSP;
    private String tenSP;
    private String tenCPU;
    private String tenGPU;
    private String tenRam;
    private String tenMH;
    private String tenOCung;
    private String pin;
    private float  giaSP;
    private int soLuongSP;
    
}
