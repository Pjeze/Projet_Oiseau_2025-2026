package fr.oiseaux.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 3D kd-tree for fixed-radius neighbor search on a periodic cube.
 * Assumes the interaction radius is at most half the box size.
 */
public class KDTree3D {
  private static final double BOX_SIZE = 100.0;
  private static final double HALF_BOX = BOX_SIZE / 2.0;

  private static final class Node {
    int index;
    int axis;
    Node left;
    Node right;
  }

  private final double[][] points;
  private final Node root;

  public KDTree3D(List<Bird> birds) {
    int n = birds.size();
    points = new double[n][3];
    for (int i = 0; i < n; i++) {
      Bird bird = birds.get(i);
      points[i][0] = bird.pos.x();
      points[i][1] = bird.pos.y();
      points[i][2] = bird.pos.z();
    }

    Integer[] indices = new Integer[n];
    for (int i = 0; i < n; i++) {
      indices[i] = i;
    }
    root = build(indices, 0, n, 0);
  }

  private Node build(Integer[] indices, int start, int end, int depth) {
    if (start >= end) {
      return null;
    }

    int axis = depth % 3;
    int mid = (start + end) / 2;
    Arrays.sort(indices, start, end, (a, b) ->
        Double.compare(points[a][axis], points[b][axis]));

    Node node = new Node();
    node.index = indices[mid];
    node.axis = axis;
    node.left = build(indices, start, mid, depth + 1);
    node.right = build(indices, mid + 1, end, depth + 1);
    return node;
  }

  public List<Integer> radiusSearch(double qx, double qy, double qz, double radius) {
    List<Integer> neighbors = new ArrayList<>();
    radiusSearch(root, qx, qy, qz, radius * radius, neighbors);
    return neighbors;
  }

  private void radiusSearch(
      Node node,
      double qx,
      double qy,
      double qz,
      double radius2,
      List<Integer> neighbors) {
    if (node == null) {
      return;
    }

    double dx = wrapDiff(qx, points[node.index][0]);
    double dy = wrapDiff(qy, points[node.index][1]);
    double dz = wrapDiff(qz, points[node.index][2]);
    if (dx * dx + dy * dy + dz * dz <= radius2) {
      neighbors.add(node.index);
    }

    double queryCoord;
    double splitCoord;
    switch (node.axis) {
      case 0 -> {
        queryCoord = qx;
        splitCoord = points[node.index][0];
      }
      case 1 -> {
        queryCoord = qy;
        splitCoord = points[node.index][1];
      }
      default -> {
        queryCoord = qz;
        splitCoord = points[node.index][2];
      }
    }

    double diff = wrapDiff(queryCoord, splitCoord);
    Node near = diff <= 0 ? node.left : node.right;
    Node far = diff <= 0 ? node.right : node.left;

    radiusSearch(near, qx, qy, qz, radius2, neighbors);
    if (diff * diff <= radius2) {
      radiusSearch(far, qx, qy, qz, radius2, neighbors);
    }
  }

  static double wrapDiff(double a, double b) {
    double diff = a - b;
    if (diff > HALF_BOX) {
      diff -= BOX_SIZE;
    } else if (diff < -HALF_BOX) {
      diff += BOX_SIZE;
    }
    return diff;
  }
}
