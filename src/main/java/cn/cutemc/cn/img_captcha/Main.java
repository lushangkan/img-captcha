 package cn.cutemc.cn.img_captcha;
 
 import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BiFunction;

import org.bukkit.Bukkit;
 import org.bukkit.Material;
 import org.bukkit.entity.Player;
 import org.bukkit.event.EventHandler;
 import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
 import org.bukkit.event.inventory.InventoryType;
 import org.bukkit.event.player.PlayerJoinEvent;
 import org.bukkit.inventory.AnvilInventory;
 import org.bukkit.inventory.EquipmentSlot;
 import org.bukkit.inventory.Inventory;
 import org.bukkit.inventory.InventoryHolder;
 import org.bukkit.inventory.ItemStack;
 import org.bukkit.inventory.meta.ItemMeta;
 import org.bukkit.map.MapRenderer;
 import org.bukkit.map.MapView;
 import org.bukkit.plugin.Plugin;
 import org.bukkit.plugin.java.JavaPlugin;
 import org.bukkit.scheduler.BukkitRunnable;
 import net.wesjd.anvilgui.*;
import net.wesjd.anvilgui.AnvilGUI.Builder;
import net.wesjd.anvilgui.AnvilGUI.Slot;
 
 public class Main
   extends JavaPlugin implements Listener {
   String sout;
   ItemStack olditem;
   Inventory inv;
   AnvilInventory Anvil;
   VerifyCode verifyCode;
   AnvilGUI.Builder GUI;
   ItemStack gh = new ItemStack(Material.WOOD_BUTTON);
   private static ArrayList<String> CAPTCHAING = new ArrayList<>();
   public void onEnable() { getServer().getPluginManager().registerEvents(this, (Plugin)this); }
 
   
   @SuppressWarnings("deprecation")
@EventHandler
   public void onPlayerJoin(PlayerJoinEvent e) {
	 ItemMeta ghMeta = gh.getItemMeta();
	 ghMeta.setDisplayName("§6点我更换验证码");
	 gh.setItemMeta(ghMeta);
     ImageRenderer renderer = null;
     verifyCode = null;
     verifyCode = new VerifyCode();
     sout = verifyCode.getText();
     ItemStack item = new ItemStack(Material.MAP, 1);
     MapView view = Bukkit.createMap(e.getPlayer().getWorld());
     Player p = e.getPlayer();
     Iterator<MapRenderer> iter = view.getRenderers().iterator();
     while (iter.hasNext()) {
       view.removeRenderer(iter.next());
     }
     try {
       renderer = new ImageRenderer(verifyCode.getImage());
     } catch (IOException e1) {
       e1.printStackTrace();
     } 
     if (renderer != null) {
       view.addRenderer(renderer);
       ItemMeta meta = item.getItemMeta();
       item.setDurability(view.getId());
       item.setItemMeta(meta);
       p.getInventory().setItemInOffHand(item);
       p.updateInventory();
       /**
       inv = Bukkit.createInventory(null, InventoryType.ANVIL, "请输入副手的验证码");
       
       inv.setItem(0, paper);
       p.openInventory(Main.this.inv);
       p.updateInventory();
       **/
    	 ItemStack paper = new ItemStack(Material.PAPER);
         
         ItemMeta papermeta = paper.getItemMeta();
         papermeta.setDisplayName("请在这里输入验证码,记得删了这段文字");
         paper.setItemMeta(papermeta);
         GUI = null;
         GUI = new Builder()
        .text("请在这里输入验证码")

        .item(paper)
        .title("请输入副手的验证码")
        .preventClose()
        
        .plugin(this)
        .onComplete((player, text) -> {                 
           if(text.equalsIgnoreCase(verifyCode.getText())) {                 
               player.sendMessage("§6[§4验证系统§6]§a:§5验证成功!");
               CAPTCHAING.remove(player.getUniqueId().toString());
               return AnvilGUI.Response.close();
           }else{
        	   return AnvilGUI.Response.text("验证码错误!");   
           }
                                                             
       });
       Bukkit.getServer().getScheduler().runTaskLater(this, new BukkitRunnable(){
             public void run() {
            	 if(CAPTCHAING.contains(p.getUniqueId().toString()) == false) {
            		 CAPTCHAING.add(p.getUniqueId().toString()); 
            		 
            	 }
            	 p.getInventory().setItem(0, gh);
                 GUI.open(p);
             }
           }, 15L);
       
     } 
   }
   @SuppressWarnings({ "static-access", "deprecation" })
   @EventHandler
   public void onInventoryClick(InventoryClickEvent e){
	   
	   Player p = (Player)e.getWhoClicked();
	   p.sendMessage("你点了物品栏");
	   Inventory inv = e.getInventory();
	   if(CAPTCHAING.contains(p.getUniqueId().toString())) {
		   p.sendMessage("你在List里");
	   }
	   if(CAPTCHAING.contains(p.getUniqueId().toString()) && e.getCurrentItem().equals(gh)) {
		   p.sendMessage("你进了第一次判断");
    	   if(e.getSlot() == 0) {
    		   p.sendMessage("你进了第二次判断");
    		   ImageRenderer renderer = null;
    		   p.getInventory().setItemInOffHand(null);
    		   verifyCode = new VerifyCode();
    		     sout = verifyCode.getText();
    		     ItemStack item = new ItemStack(Material.MAP, 1);
    		     MapView view = Bukkit.createMap(p.getWorld());
    		     Iterator<MapRenderer> iter = view.getRenderers().iterator();
    		     while (iter.hasNext()) {
    		       view.removeRenderer(iter.next());
    		     }
    		     try {
    		       renderer = new ImageRenderer(verifyCode.getImage());
    		     } catch (IOException e1) {
    		       e1.printStackTrace();
    		     } 
    		     if (renderer != null) {
    		       view.addRenderer(renderer);
    		       ItemMeta meta = item.getItemMeta();
    		       item.setDurability(view.getId());
                   item.setItemMeta(meta);
    		       p.getInventory().setItemInOffHand(item);
    		       p.updateInventory();
    		     }
    	   }
    		   
       }
   }
   }
 
 