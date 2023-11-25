/*
 *
 * Red-Black Tree Project
 * Data Structures Course
 * 10/NOV/2018
 *
 * Alejandro Hahn Gallegos (A01561774)
 * Yessica Janeth Hernandez Rosales (A01561794)
 * Hugo Alberto Valencia Corral (A01561688)
 *
 */

#ifndef REDBLACKTREE_REDBLACKTREE_H
#define REDBLACKTREE_REDBLACKTREE_H

#include <cassert>
#include <vector>
#include <iostream>
#include <iomanip>
#include <string>

/*
 * ---------------------------------------------------------
 * Utility Enumerations
 * ---------------------------------------------------------
 */

enum Color { RED, BLACK };
enum WalkOrder { PREORDER, INORDER, POSTORDER };

/*
 * ---------------------------------------------------------
 * Classes Declaration
 * ---------------------------------------------------------
 */
template <typename T>
class RedBlackNode;

template <typename T>
class RedBlackTree;

/*
 * ---------------------------------------------------------
 * Node Implementation
 * ---------------------------------------------------------
 */
template <typename T>
class RedBlackNode {

public:
    T value = {};
    Color color = BLACK;
    RedBlackNode<T>* left = nullptr;
    RedBlackNode<T>* right = nullptr;
    RedBlackNode<T>* parent = nullptr;

    /*
     * ---------------------------------------------------------
     * Rotations
     * ---------------------------------------------------------
     */

    static void RotateLeft(RedBlackTree<T>* t, RedBlackNode<T>* x) {
        auto y = x->right;
        x->right = y->left;
        if(y->left != nullptr) {
            y->left->parent = x;
        }
        y->parent = x->parent;
        if(x->parent == nullptr) {
            t->root = y;
        } else if(x == x->parent->left) {
            x->parent->left = y;
        } else {
            x->parent->right = y;
        }
        y->left = x;
        x->parent = y;
    }

    static void RotateRight(RedBlackTree<T>* t, RedBlackNode<T>* x) {
        auto y = x->left;
        x->left = y->right;
        if(y->right != nullptr) {
            y->right->parent = x;
        }
        y->parent = x->parent;
        if(x->parent == nullptr) {
            t->root = y;
        } else if(x == x->parent->right) {
            x->parent->right = y;
        } else {
            x->parent->left = y;
        }
        y->right = x;
        x->parent = y;
    }

};

/*
 * ---------------------------------------------------------
 * Tree Implementation
 * ---------------------------------------------------------
 */
template <typename T>
class RedBlackTree {

public:
    RedBlackNode<T>* root = nullptr;

    /*
     * ---------------------------------------------------------
     * Public Methods
     * ---------------------------------------------------------
     */

    /*
     * ---------------------------------------------------------
     * Basic Operations (Insert, Delete, Height, Size and Search)
     * ---------------------------------------------------------
     */

    void Insert(T value) {
        RedBlackNode<T>* x = new RedBlackNode<T>();
        x->value = value;
        InsertNode(x);
        root->color = BLACK;
    }

    void Delete(RedBlackNode<T>* x) {
        DeleteNodeV2(x);
    }

    void Delete(T value) {
        RedBlackNode<T>* x = Search(value);
        if(x) {
            DeleteNodeV2(x);
        }
    }

    RedBlackNode<T>* Search(T key) {
        auto current = root;
        while (current) {
            if(key == current->value) {
                return current;
            } else if (key < current->value) {
                current = current->left;
            } else {
                current = current->right;
            }
        }
        return nullptr;
    }

    int Size() {
        if(root == nullptr) {
            return 0;
        } else {
            return (SizeRecursive(root->left) + 1 + SizeRecursive(root->right));
        }
    }

    int Height() {
        return HeightRecursive(root);
    }

    /*
     * ---------------------------------------------------------
     * Tree to Vector Conversion
     * ---------------------------------------------------------
     */

    virtual std::vector<RedBlackNode<T>*> ToList(WalkOrder x) {
        std::vector<RedBlackNode<T>*> list;
        if(x == PREORDER) {
            ToListPreOrder(root, &list);
        } else if (x == INORDER) {
            ToListInOrder(root, &list);
        } else {
            ToListPostOrder(root, &list);
        }
        return list;
    }

    /*
     * ---------------------------------------------------------
     * Printing (1D and 2D)
     * ---------------------------------------------------------
     */

    virtual void Print(WalkOrder x, bool c) {
        if(x == PREORDER) {
            PrintPreOrder(root, c);
        } else if (x == INORDER) {
            PrintInOrder(root, c);
        } else {
            PrintPostOrder(root, c);
        }
    }

    virtual void Print(WalkOrder x, RedBlackNode<T>* n, bool c) {
        if(x == PREORDER) {
            PrintPreOrder(n, c);
        } else if (x == INORDER) {
            PrintInOrder(n, c);
        } else {
            PrintPostOrder(n, c);
        }
    }

    virtual void PrintDefault(bool c) {
        Print(INORDER, c);
    }

    virtual void PrintDefault(RedBlackNode<T>* n, bool c) {
        Print(INORDER, n, c);
    }

    virtual void Print2D(bool c)
    {
        Print2DUtil(root, 0, c);
    }

    virtual void Print2D(RedBlackNode<T>* n, bool c)
    {
        Print2DUtil(n, 0, c);
    }

private:

    /*
     * ---------------------------------------------------------
     * Private Methods
     * ---------------------------------------------------------
     */

    /*
     * ---------------------------------------------------------
     * Basic Operations Logic
     * ---------------------------------------------------------
     */

    void InsertNode(RedBlackNode<T>* z) {
        RedBlackNode<T>* y = nullptr;
        auto x = root;
        while(x != nullptr) {
            y = x;
            if(z->value < x->value) {
                x = x->left;
            } else {
                x = x->right;
            }
        }
        z->parent = y;
        if(y == nullptr) {
            root = z;
        } else if(z->value < y->value) {
            y->left = z;
        } else {
            y->right = z;
        }
        z->left = nullptr;
        z->right = nullptr;
        z->color = RED;
        InsertRepair(z);
    }

    void DeleteNode(RedBlackNode<T>* z) {
        auto y = z;
        auto yOriginalColor = y->color;
        RedBlackNode<T>* x;
        if(z->left == nullptr) {
            x = z->right;
            Swap(z, z->right);
        } else if(z->right == nullptr) {
            x = z->left;
            Swap(z, z->left);
        } else {
            y = Minimum(z->right);
            yOriginalColor = y->color;
            x = z->right;
            if(y->parent == z) {
                x->parent = y;
            } else {
                Swap(y, y->right);
                y->right = z->right;
                y->right->parent = y;
            }
            Swap(z, y);
            y->left = z->left;
            y->left->parent = y;
            y->color = z->color;
        }
        if(yOriginalColor == BLACK) {
            DeleteRepair(x);
        }
    }

    void DeleteNodeV2(RedBlackNode<T>* z) {
        Color c = z->color;
        if(z->left == nullptr) {
            Swap(z, z->right);
        } else if(z->right == nullptr) {
            Swap(z, z->left);
        } else {
            auto y = Minimum(z->right);
            if(y->parent != z) {
                Swap(y, y->right);
                y->right = z->right;
                y->right->parent = y;
            }
            Swap(z, y);
            y->left = z->left;
            y->left->parent = y;
        }

        if(c == BLACK) {
            auto w = Minimum(root);
            T key = w->value;
            if(w->left == nullptr) {
                Swap(w, w->right);
            } else if(w->right == nullptr) {
                Swap(w, w->left);
            } else {
                auto y = Minimum(w->right);
                if(y->parent != w) {
                    Swap(y, y->right);
                    y->right = w->right;
                    y->right->parent = y;
                }
                Swap(w, y);
                y->left = w->left;
                y->left->parent = y;
            }
            Insert(key);
        }
    }

    /*
     * ---------------------------------------------------------
     * Basic Operations Utilities
     * ---------------------------------------------------------
     */

    void InsertRepair(RedBlackNode<T>* z) {
        if(!(z->parent) || !(z->parent->parent)) {
            return;
        }
        RedBlackNode<T>* y;
        while (z->parent != nullptr && z->parent->color == RED) {
            if(z->parent == z->parent->parent->left) {
                y = z->parent->parent->right;
                if(y && y->color == RED) {
                    z->parent->color = BLACK;
                    y->color = BLACK;
                    z->parent->parent->color = RED;
                    z = z->parent->parent;
                } else if(z->parent && z == z->parent->right) {
                    z = z->parent;
                    RedBlackNode<T>::RotateLeft(this, z);
                } else {
                    z->parent->color = BLACK;
                    z->parent->parent->color = RED;
                    RedBlackNode<T>::RotateRight(this, z->parent->parent);
                }
            } else {
                y = z->parent->parent->left;
                if(y && y->color == RED) {
                    z->parent->color = BLACK;
                    y->color = BLACK;
                    z->parent->parent->color = RED;
                    z = z->parent->parent;
                } else if(z->parent && z == z->parent->left) {
                    z = z->parent;
                    RedBlackNode<T>::RotateRight(this, z);
                } else {
                    z->parent->color = BLACK;
                    z->parent->parent->color = RED;
                    RedBlackNode<T>::RotateLeft(this, z->parent->parent);
                }
            }
            root->color = BLACK;
        }
        root->color = BLACK;

    }

    void DeleteRepair(RedBlackNode<T>* x) {
        while (x && x != root && x->color == BLACK) {
            if(x == x->parent->left) {
                auto w = x->parent->right;
                if(w->color == RED) {
                    w->color = BLACK;
                    x->parent->color = RED;
                    RedBlackNode<T>::RotateLeft(this, x->parent);
                    w = x->parent->right;
                }
                if(w->left && w->right && w->left->color == BLACK && w->right->color == BLACK) {
                    w->color = RED;
                    x = x->parent;
                } else if (w->right && w->right->color == BLACK) {
                    if(w->left) {
                        w->left->color = BLACK;
                    }
                    w->color = RED;
                    RedBlackNode<T>::RotateRight(this, w);
                    w = x->parent->right;
                } else {
                    w->color = x->parent->color;
                    x->parent->color = BLACK;
                    if(w->right) {
                        w->right->color = BLACK;
                    }
                    RedBlackNode<T>::RotateLeft(this, x->parent);
                    x = root;
                }
            } else {
                auto w = x->parent->left;
                if(w->color == RED) {
                    w->color = BLACK;
                    x->parent->color = RED;
                    RedBlackNode<T>::RotateRight(this, x->parent);
                    w = x->parent->left;
                }
                if(w->right && w->left && w->right->color == BLACK && w->left->color == BLACK) {
                    w->color = RED;
                    x = x->parent;
                } else if (w->left && w->left->color == BLACK) {
                    if(w->right) {
                        w->right->color = BLACK;
                    }
                    w->color = RED;
                    RedBlackNode<T>::RotateLeft(this, w);
                    w = x->parent->left;
                } else {
                    w->color = x->parent->color;
                    x->parent->color = BLACK;
                    if(w->left) {
                        w->left->color = BLACK;
                    }
                    RedBlackNode<T>::RotateRight(this, x->parent);
                    x = root;
                }
            }
        }
        if(x) {
            x->color = BLACK;
        }

    }

    void Swap(RedBlackNode<T>* x, RedBlackNode<T>* y) {
        if(x->parent == nullptr) {
            root = y;
        } else if(x == x->parent->left) {
            x->parent->left = y;
        } else {
            x->parent->right = y;
        }
        if(y && x) {
            y->parent = x->parent;
        }
    }

    RedBlackNode<T>* Minimum(RedBlackNode<T>* x) {
        while (x->left != nullptr) {
            x = x->left;
        }
        return x;
    }

    /*
     * ---------------------------------------------------------
     * Height and Size Logic
     * ---------------------------------------------------------
     */

    int HeightRecursive(RedBlackNode<T>* x) {
        if(!x) {
            return 0;
        } else {
            int l = HeightRecursive(x->left);
            int r = HeightRecursive(x->right);

            if (l > r) {
                return (l + 1);
            } else {
                return (r + 1);
            }
        }
    }

    int SizeRecursive(RedBlackNode<T>* n) {
        if(n == nullptr) {
            return 0;
        } else {
            return (SizeRecursive(n->left) + 1 + SizeRecursive(n->right));
        }
    }

    /*
     * ---------------------------------------------------------
     * To Vector Conversion Utilities
     * ---------------------------------------------------------
     */

    void ToListPreOrder(RedBlackNode<T>* n, std::vector<RedBlackNode<T>*>* list) {
        if(!n) {
            return;
        }
        list->push_back(n);
        ToListPreOrder(n->left, list);
        ToListPreOrder(n->right, list);
    }

    void ToListInOrder(RedBlackNode<T>* n, std::vector<RedBlackNode<T>*>* list) {
        if(!n) {
            return;
        }
        ToListInOrder(n->left, list);
        list->push_back(n);
        ToListInOrder(n->right, list);
    }

    void ToListPostOrder(RedBlackNode<T>* n, std::vector<RedBlackNode<T>*>* list) {
        if(!n) {
            return;
        }
        ToListPostOrder(n->left, list);
        ToListPostOrder(n->right, list);
        list->push_back(n);
    }

    /*
     * ---------------------------------------------------------
     * Printing Utilities
     * ---------------------------------------------------------
     */

    virtual void PrintPreOrder(RedBlackNode<T>* n, bool c) {
        if(!n) {
            return;
        }
        std::string s = "";
        if(c) {
            s = root->color == BLACK ? "(B)" : "(R)";
        }
        std::cout << n->value << s << " ";
        PrintPreOrder(n->left, c);
        PrintPreOrder(n->right, c);
    }

    virtual void PrintInOrder(RedBlackNode<T>* n, bool c) {
        if(!n) {
            return;
        }
        PrintInOrder(n->left, c);
        std::string s = "";
        if(c) {
            s = root->color == BLACK ? "(B)" : "(R)";
        }
        std::cout << n->value << s << " ";
        PrintInOrder(n->right, c);
    }

    virtual void PrintPostOrder(RedBlackNode<T>* n, bool c) {
        if(!n) {
            return;
        }
        PrintPostOrder(n->left, c);
        PrintPostOrder(n->right, c);
        std::string s = "";
        if(c) {
            s = root->color == BLACK ? "(B)" : "(R)";
        }
        std::cout << n->value  << s << " ";
    }

    virtual void Print2DUtil(RedBlackNode<T>* root, int space, bool c)
    {
        if (root == NULL) {
            return;
        }
        space += 4;
        Print2DUtil(root->right, space, c);
        std::cout << std::endl;
        for (int i = 4; i < space; i++) {
            std::cout << " ";
        }
        std::string s = "";
        if(c) {
            s = root->color == BLACK ? "(B)" : "(R)";
        }
        std::cout << root->value <<  s << " " << std::endl;
        Print2DUtil(root->left, space, c);
    }

};


#endif //REDBLACKTREE_REDBLACKTREE_H