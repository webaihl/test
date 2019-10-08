package com.web.designpattern.factory.simple;

class ImgReadFactory implements ReadFactory {

    @Override
    public Read getRead() {
        return new ReadImg();
    }
}

class TextReadFactory implements ReadFactory {

    @Override
    public Read getRead() {
        return new ReadTxt();
    }
}

class Mp3ReadFactory implements ReadFactory {

    @Override
    public Read getRead() {
        return new ReadMp3();
    }

}
