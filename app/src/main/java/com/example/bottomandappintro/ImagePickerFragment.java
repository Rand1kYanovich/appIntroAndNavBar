package com.example.bottomandappintro;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.myhexaville.smartimagepicker.ImagePicker;
import com.example.bottomandappintro.databinding.TestLayoutBinding;

import java.io.File;


public class ImagePickerFragment extends Fragment {

    private ImagePicker imagePicker;
    private TestLayoutBinding binding;
    private ImageView imageView;

    public ImagePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.test_layout, container, false);

        binding.openCamera.setOnClickListener(v -> openCamera());
        binding.showAll.setOnClickListener(v -> showAll());
        binding.showGallery.setOnClickListener(v -> chooseFromGallery());
        imageView = (ImageView)binding.getRoot().findViewById(R.id.image);
        File file = imagePicker.getImageFile();
        

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode, requestCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
    }

    public void showAll() {
        refreshImagePicker();
        imagePicker.choosePicture(true);
    }

    public void chooseFromGallery() {
        refreshImagePicker();
        imagePicker.choosePicture(false);
    }

    public void openCamera() {
        refreshImagePicker();
        imagePicker.openCamera();
    }

    private void refreshImagePicker() {
        imagePicker = new ImagePicker(getActivity(),
                this,
                imageUri -> {
                    binding.image.setImageURI(imageUri);
                });
        if (binding.withCrop.isChecked()) {
            imagePicker.setWithImageCrop(
                    Integer.parseInt(binding.aspectRatioX.getText().toString()),
                    Integer.parseInt(binding.aspectRatioY.getText().toString())
            );
        }
    }

}