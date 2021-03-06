package dsandalgo.design;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode.com/problems/design-in-memory-file-system/
 * Design an in-memory file system to simulate the following functions:
 *
 * ls: Given a path in string format. If it is a file path, return a list that only contains this file's name. If it is a directory path, return the list of file and directory names in this directory. Your output (file and directory names together) should in lexicographic order.
 *
 * mkdir: Given a directory path that does not exist, you should make a new directory according to the path. If the middle directories in the path don't exist either, you should create them as well. This function has void return type.
 *
 * addContentToFile: Given a file path and file content in string format. If the file doesn't exist, you need to create that file containing given content. If the file already exists, you need to append given content to original content. This function has void return type.
 *
 * readContentFromFile: Given a file path, return its content in string format.
 *
 *
 *
 * Example:
 *
 * Input:
 * ["FileSystem","ls","mkdir","addContentToFile","ls","readContentFromFile"]
 * [[],["/"],["/a/b/c"],["/a/b/c/d","hello"],["/"],["/a/b/c/d"]]
 *
 * Output:
 * [null,[],null,null,["a"],"hello"]
 *
 * Explanation:
 * filesystem
 *
 *
 * Note:
 *
 * You can assume all file or directory paths are absolute paths which begin with / and do not end with / except that the path is just "/".
 * You can assume that all operations will be passed valid parameters and users will not attempt to retrieve file content or list a directory or file that does not exist.
 * You can assume that all directory names and file names only contain lower-case letters, and same names won't exist in the same directory.
 */
public class FileSystem {

    class File {
        boolean isFile = false;
        Map<String, File> children = new HashMap<>();
        String content = "";
    }

    private File root;

    public FileSystem() {
        root = new File();
    }

    public List<String> ls(String path) {
        String[] dirs = path.split("/");
        File node = root;
        String name = "";
        List<String> res = new ArrayList<String>();
        for (String dir : dirs) {
            if (dir.length() == 0) continue;
            if (!node.children.containsKey(dir)) {
                return res;
            }
            node = node.children.get(dir);
            name = dir;
        }
        if (node.isFile) {
            res.add(name);
        } else {
            for (String str : node.children.keySet()) {
                res.add(str);
            }
            Collections.sort(res);
        }
        return res;
    }

    public void mkdir(String path) {
        traverse(path);
    }

    public void addContentToFile(String filePath, String content) {
        File cur = traverse(filePath);
        cur.isFile = true;
        cur.content += content;
    }

    public String readContentFromFile(String filePath) {
        File cur = traverse(filePath);
        return cur.content;
    }

    private File traverse(String filePath) {
        String[] dirs = filePath.split("/");
        File cur = root;
        for (String dir : dirs) {
            if (dir.length() == 0) {
                continue;
            }
            cur.children.putIfAbsent(dir, new File());
            cur = cur.children.get(dir);
        }
        return cur;
    }
}
