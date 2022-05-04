package me.superbility.superbosses.utils;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.world.DataException;
import me.superbility.superbosses.Main;
import org.bukkit.Location;

import java.io.File;
import java.io.IOException;

public class Schematic {
    private static Main plugin = Main.getPlugin(Main.class);

    public static void paste(String schematicName, Location pasteLoc) {
        try {
            File dir = new File(plugin.getDataFolder(), "/schematics/" + schematicName + ".schematic");

            EditSession editSession = new EditSession(new BukkitWorld(pasteLoc.getWorld()), 999999999);
            editSession.enableQueue();

            SchematicFormat schematic = SchematicFormat.getFormat(dir);
            CuboidClipboard clipboard = schematic.load(dir);

            clipboard.paste(editSession, BukkitUtil.toVector(pasteLoc), false);
            editSession.flushQueue();
        } catch (DataException | IOException ex) {
            //ex.printStackTrace();
        } catch (MaxChangedBlocksException ex) {
            //ex.printStackTrace();
        }
    }
    /*
    public static void paste(String schematicName, Location pasteLoc) {
        try {
            File dir = new File(plugin.getDataFolder(), "/schematics/" + schematicName + ".schematic");
            ClipboardFormat format = ClipboardFormats.findByFile(dir);
            ClipboardReader reader = null;
            Clipboard clipboard = null;
            BlockVector3 to = BlockVector3.at(pasteLoc.getX(), pasteLoc.getBlockY(), pasteLoc.getZ());

            try {
                reader = format.getReader(new FileInputStream(dir));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                clipboard = reader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(FaweAPI.getWorld(pasteLoc.getWorld().getName()), -1)) {
                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(to)
                        .ignoreAirBlocks(false)
                        .build();
                Operations.complete(operation);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    */
}
