/*
 * Copyright (c) 2003-2006 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors 
 *   may be used to endorse or promote products derived from this software 
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package jmetest.renderer.loader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import com.jme.app.SimpleGame;
import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.scene.Spatial;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;
import com.jme.util.export.binary.BinaryImporter;
import com.jmex.model.XMLparser.Converters.Md3ToJme;

/**
 * Started Date: Jul 15, 2004<br><br>
 *
 * Test the ability to load MD3 files.
 * 
 * @author Jack Lindamood
 */
public class TestMd3JmeWrite extends SimpleGame{
    public static void main(String[] args) {
        TestMd3JmeWrite app=new TestMd3JmeWrite();
        app.setDialogBehaviour(SimpleGame.ALWAYS_SHOW_PROPS_DIALOG);
        app.start();
    }
    protected void simpleInitGame() {
        Md3ToJme converter=new Md3ToJme();
        URL laura=null;
        laura=TestMd3JmeWrite.class.getClassLoader().getResource("jmetest/data/model/lara/lara_lower.md3");
        URL tex=TestMd3JmeWrite.class.getClassLoader().getResource("jmetest/data/model/lara/default.bmp");
        ByteArrayOutputStream BO=new ByteArrayOutputStream();
        try {
            converter.convert(laura.openStream(),BO);
            System.out.println("Done converting, now watch how fast it loads!");
            long time=System.currentTimeMillis();
            Spatial r=(Spatial)BinaryImporter.getInstance().load(new ByteArrayInputStream(BO.toByteArray()));
            r.setModelBound(new BoundingBox());
            r.updateModelBound();
            System.out.println("Finished loading time is "+(System.currentTimeMillis()-time));
            TextureState ts=display.getRenderer().createTextureState();
            ts.setTexture(TextureManager.loadTexture(tex,Texture.MM_LINEAR,Texture.FM_LINEAR));
            ts.setEnabled(true);
            r.setRenderState(ts);
            rootNode.attachChild(r);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}