package joshie.enchiridion.library;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import joshie.enchiridion.helpers.UUIDHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.WorldSavedData;
import net.minecraftforge.common.UsernameCache;

public class LibrarySavedData extends WorldSavedData {
    public static final String DATA_NAME = "Enchiridion-Library";
    private HashMap<UUID, LibraryInventory> players = new HashMap();

    public LibrarySavedData(String string) {
        super(string);
    }

    public Collection<LibraryInventory> getPlayerData() {
        return players.values();
    }

    public LibraryInventory getLibraryContents(EntityPlayerMP player) {
        UUID uuid = UUIDHelper.getPlayerUUID(player);
        if (players.containsKey(uuid)) {
            return players.get(uuid);
        } else {
            //If this UUID was not found, Search the username cache for this players username
            String name = player.getGameProfile().getName();
            for (Map.Entry<UUID, String> entry : UsernameCache.getMap().entrySet()) {
                if (entry.getValue().equals(name)) {
                    uuid = entry.getKey();
                    break;
                }
            }

            if (players.containsKey(uuid)) {
                return players.get(uuid);
            } else {
                LibraryInventory data = new LibraryInventory(player);
                players.put(uuid, data);

                markDirty();
                return players.get(uuid);
            }
        }
    }

    /** CAN AND WILL RETURN NULL, IF THE UUID COULD NOT BE FOUND **/
    public LibraryInventory getLibraryContents(UUID uuid) {
        if (players.containsKey(uuid)) {
            return players.get(uuid);
        } else {
            EntityPlayer player = UUIDHelper.getPlayerFromUUID(uuid);
            if (player == null) return null;
            else return getLibraryContents((EntityPlayerMP) player);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagList tag_list_players = nbt.getTagList("LibraryInventory", 10);
        for (int i = 0; i < tag_list_players.tagCount(); i++) {
            NBTTagCompound tag = tag_list_players.getCompoundTagAt(i);
            LibraryInventory data = new LibraryInventory();
            boolean success = false;
            try {
                data.readFromNBT(tag);
                success = true;

            } catch (Exception e) {
                success = false;
            }

            //Only add non failed loads
            if (success) {
                players.put(data.getUUID(), data);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        NBTTagList tag_list_players = new NBTTagList();
        for (Map.Entry<UUID, LibraryInventory> entry : players.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                NBTTagCompound tag = new NBTTagCompound();
                entry.getValue().writeToNBT(tag);
                tag_list_players.appendTag(tag);
            }
        }

        nbt.setTag("LibraryInventory", tag_list_players);
    }
}