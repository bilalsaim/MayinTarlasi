package com.bilisel.mayintarlasi.model;

import android.content.res.TypedArray;

public class SelectedList{
    int indeks;
    TypedArray typedArray;

    public SelectedList(int indeks, TypedArray typedArray) {
        this.indeks = indeks;
        this.typedArray = typedArray;
    }

    public int getSecilen() {
        return typedArray.getResourceId(indeks, -1);
    }

    public int getIndeks() {
        return indeks;
    }

    public void setIndeks(int indeks) {
        this.indeks = indeks;
    }

    public TypedArray getTypedArray() {
        return typedArray;
    }

    public void setTypedArray(TypedArray typedArray) {
        this.typedArray = typedArray;
    }
}
