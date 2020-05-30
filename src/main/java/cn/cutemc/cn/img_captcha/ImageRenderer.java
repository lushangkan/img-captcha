package cn.cutemc.cn.img_captcha;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URL;

import javax.imageio.ImageIO;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class ImageRenderer extends MapRenderer {
	 
    private boolean hasRendered = false;
    public Image img;
 
    public ImageRenderer(Image image) throws IOException {
       img = image;
    }
 
    @Override
    public void render(MapView view, MapCanvas canvas, Player player){
        if(this.hasRendered){
            return;
        }
 
        if(this.img != null){
            canvas.drawImage(0, 0,img);
            this.hasRendered = true;
        } else {
            this.hasRendered = true;
        }
    }
 

 
}
