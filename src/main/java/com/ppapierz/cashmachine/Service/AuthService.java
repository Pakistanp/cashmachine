package com.ppapierz.cashmachine.Service;

import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class AuthService {

    private String getOpenFilePath() {
        FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        return dialog.getDirectory() + "\\" + dialog.getFile();
    }
}
