//
// Created by Alejandro on 11/11/2018.
//

#ifndef REDBLACKTREE_AREDBLACKTREE_H
#define REDBLACKTREE_AREDBLACKTREE_H

#endif //REDBLACKTREE_AREDBLACKTREE_H

#include <string>
#include <iostream>
#include <sstream>

#include "redblacktree.h"

template <typename T>
class ARedBlackTree : public RedBlackTree<T> {
public:

    /*
     * ---------------------------------------------------------
     * Android Specific Printing Methods
     * ---------------------------------------------------------
     */

    std::string APrint(WalkOrder x = PREORDER, bool c = false) {
      output = "";
      if(x == PREORDER) {
        APrintPreOrder(this->root, c);
      } else if (x == INORDER) {
        APrintInOrder(this->root, c);
      } else {
        APrintPostOrder(this->root, c);
      }
      return output;
    }

    std::string APrint2D(RedBlackNode<T>* root, bool c) {
      output = "";
      APrint2DUtil(root, 0, c);
      return output;
    }

    std::string APrint2D(bool c = false) {
      return APrint2D(this->root, c);
    }

private:

    /*
     * ---------------------------------------------------------
     * QT Specific Printing Utilities
     * ---------------------------------------------------------
     */

    std::string output = "";

    void APrintInOrder(RedBlackNode<T>* n, bool c) {
      if(!n) {
        return;
      }
      APrintInOrder(n->left, c);
      output += std::to_string(n->value);
      if(c) {
        std::string color = n->color == BLACK ? "(B)" : "(R)";
        output += " " + color + " ";
      }
      output+= " ";
      APrintInOrder(n->right, c);
    }

    void APrintPostOrder(RedBlackNode<T>* n, bool c) {
      if(!n) {
        return;
      }
      APrintPostOrder(n->left, c);
      APrintPostOrder(n->right, c);
      output += std::to_string(n->value);
      if(c) {
        std::string color = n->color == BLACK ? "(B)" : "(R)";
        output += " " + color + " ";
      }
      output += " ";
    }

    void APrintPreOrder(RedBlackNode<T>* n, bool c) {
      if(!n) {
        return;
      }
      output += std::to_string(n->value);
      if(c) {
        std::string color = n->color == BLACK ? "(B)" : "(R)";
        output += " " + color + " ";
      }
      output += " ";
      APrintPreOrder(n->left, c);
      APrintPreOrder(n->right, c);
    }

    void APrint2DUtil(RedBlackNode<T>* root, int space, bool c)
    {
      if (root == NULL) {
        return;
      }
      space += 10;
      APrint2DUtil(root->right, space, c);
      output += "\n";
      for (int i = 10; i < space; i++) {
        output += " ";
      }
      output += std::to_string(root->value);
      if(c) {
        std::string color = root->color == BLACK ? "(B)" : "(R)";
        output += " " + color + " ";
      }
      output += "\n";
      APrint2DUtil(root->left, space, c);
    }
};
