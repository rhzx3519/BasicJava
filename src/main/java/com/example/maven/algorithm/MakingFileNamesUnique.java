package com.example.maven.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2021/01/29
 */
@Slf4j
public class MakingFileNamesUnique {
    private static class Solution {
        public String[] getFolderNames(String[] names) {
            String[] ans = new String[names.length];
            Map<String, Integer> fileMap =  new HashMap<>();
            for(int i = 0; i < names.length; i++) {
                String name = names[i];
                if (fileMap.containsKey(name)) {
                    int k = fileMap.get(name) + 1;
                    while (true) {
                        String newName = name + "(" + k + ")";
                        if (!fileMap.containsKey(newName)) {
                            fileMap.put(name, k);
                            fileMap.put(newName, 0);
                            ans[i] = newName;
                            break;
                        }
                        k += 1;
                    }
                } else {
                    ans[i] = name;
                    fileMap.put(name, 0);
                }
            }
            return ans;
        }

    }

    public static void main(String[] args) {
        String[] names = {"gta","gta(2)","gta","avalon"};
        Solution su = new Solution();
        String[] ans = su.getFolderNames(names);
        for (String name : ans) {
            log.info(name);
        }
    }

}
