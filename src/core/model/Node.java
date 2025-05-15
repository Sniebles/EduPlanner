/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.model;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author scues
 */
public class Node {
    public final Set<Node> adj = new HashSet<>();
    public int color = 0;
}
