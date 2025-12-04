package com.mmdev.meowmayo.utils;

import net.querz.nbt.io.NBTDeserializer;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.ListTag;
import net.querz.nbt.tag.Tag;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.zip.GZIPInputStream;

public class NbtDecoder {
    private static final String ITEM_LIST_TAG = "i";
    private static final String MINECRAFT_TAG = "tag";
    private static final String EXTRA_ATTRIBUTES_TAG = "ExtraAttributes";
    private static final String SKYBLOCK_ID_TAG = "id";

    public static String decodeItemId(String itemBytes) {
        if (itemBytes == null || itemBytes.isEmpty()) {
            return null;
        }

        try {
            byte[] compressedData = Base64.getDecoder().decode(itemBytes);

            InputStream dataStream = new GZIPInputStream(new ByteArrayInputStream(compressedData));
            NamedTag tag = new NBTDeserializer(false).fromStream(dataStream);
            Tag<?> rootTag = tag.getTag();

            if (!(rootTag instanceof CompoundTag)) {
                return null;
            }

            CompoundTag outerRoot = (CompoundTag) rootTag;

            if (!outerRoot.containsKey(ITEM_LIST_TAG)) {
                return null;
            }

            ListTag<?> itemListTag = outerRoot.getListTag(ITEM_LIST_TAG);

            if (itemListTag.size() == 0) {
                return null;
            }

            ListTag<CompoundTag> itemList = itemListTag.asCompoundTagList();
            CompoundTag itemRoot = itemList.get(0);

            if (!itemRoot.containsKey(MINECRAFT_TAG)) {
                return null;
            }

            CompoundTag mcTag = itemRoot.getCompoundTag(MINECRAFT_TAG);

            if (mcTag.containsKey(EXTRA_ATTRIBUTES_TAG)) {
                CompoundTag extraAttributes = mcTag.getCompoundTag(EXTRA_ATTRIBUTES_TAG);

                return extraAttributes.getString(SKYBLOCK_ID_TAG);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
