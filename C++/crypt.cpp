//
// Created by Aspire on 12/12/2019.
//




#include <queue>
#include <string>
#include <iostream>
#include <iterator>
#include <map>
#include <algorithm>
#include <functional>
#include <set>
#include <iomanip>
#include <fstream>

using namespace std;
struct node
{
    string i;
    node *left;
    node *right;
};

void print(node* p) {
    if (p != NULL) {
        cout << p->i << "(";
        print(p->left);
        cout << ",";
        print(p->right);
        cout << ")";
    }
}
// def countLetters(s:String): Map[String,Int] =
//        s.toCharArray.map(c=>(c.toString,s.toCharArray.count(vc=>vc==c))).toMap
//
map<string, int> countLetters(string str){
    int sSize =1000;
    map<char, int> cm;
    map<string,int > cmS;
    map<char, int>::iterator itr;

    int  *countedArr = new int[sSize];
    for(long i =0 ;i < str.length();i++ ){
        countedArr[str[i]] +=1 ;
    }
    for(long i =0 ;i < str.length();i++ ){
        cm.insert(pair<char, int>(str[i], countedArr[str[i]]));
    }
    for (itr = cm.begin(); itr != cm.end(); ++itr) {
        string s(1, itr->first);
        cmS.insert(pair<string,int>( s,itr->second));
    }
    return cmS;

}

/*
def fillTree(cm: Array[String]):N={
        var q = mutable.Queue[(N,Array[String])]()
        var root = new N(cm(0))
        q.+=((root,cm.filter(!_.equals(cm(0)))))
        while(q.nonEmpty) {
            var (n,arr) = q.dequeue()
            if(arr.nonEmpty) {
                var nl = new N(arr(0))
                n.left = nl
                if (arr(0).length > 1) q.+=((nl, arr.filter(v => arr(0).contains(v) && !v.equals(arr(0)))))
                if(arr.length>1) {
                    var nr = new N(arr(1))
                    n.right = nr
                    if (arr(1).length > 1) q.+=((nr, arr.filter(v => arr(1).contains(v) && !v.equals(arr(1)))))
                }
            }
        }
        return root
}
*/

 node* fillTree(vector<string> sc){
    static node* root = new node();
    root->left = NULL;
    root->right = NULL;
    root->i = string(sc[0]);
    std::queue <pair<node*,vector<string>>> q;
    q.push((make_pair(root,std::vector<string>(sc.begin() + 1, sc.end()))));
   while(!q.empty()) {
       pair< node *, vector<string>> p = q.front();
       q.pop();
       vector<string> v = p.second;
       node *n = p.first;
       if (!v.empty()) {
           node *nl = new node();
           n->left = nl;
           nl->left = NULL;
           nl->right = NULL;
           nl->i = string(v[0]);
           vector<string> nn;
           if (v.front().size() > 1) {
               for (string s:v) {
                   if (v[0].find(s) != std::string::npos && v[0] != s)
                       nn.push_back(s);
                   q.push(make_pair(nl, nn));
               }
           }

               if (v.size() > 1) {
                  node *nr = new node();
                   n->right = nr;
                   nr->left = NULL;
                   nr->right = NULL;
                   nr->i = string(v[1]);
                   vector<string> nn;
                   if (v[1].size() > 1) {
                       for (string s:v) {
                           if (v[1].find(s) != std::string::npos && v[1] != s)
                               nn.push_back(s);
                           q.push(make_pair(nr, nn));
                       }

                   }
               }
       }
   }
    return root;


}

void codeTree(node* n,string prefix,map<char, string> &mp){
     if(n->i.length()==1){
         mp.insert(pair<char,string>((char)n->i[0],prefix));
         return;
     }
     codeTree(n->left,prefix + "0",mp);
     codeTree(n->right,prefix + "1",mp);
 }


string code(string text,node * &tree){
    map<string, int> cm = countLetters(text);
    long size = cm.size()-1;
    map<char, string>::iterator itr;
    typedef std::function<bool(std::pair<std::string, int>, std::pair<std::string, int>)> Comparator;

    // Defining a lambda function to compare two pairs. It will compare two pairs using second field
    Comparator compFunctor =
            [](std::pair<std::string, int> elem1 ,std::pair<std::string, int> elem2)
            {
                return elem1.second != elem2.second ? elem1.second < elem2.second:elem1.first < elem2.first;
            };

    Comparator compFunctorDesc =
            [](std::pair<std::string, int> elem1 ,std::pair<std::string, int> elem2)
            {
                return elem1.second != elem2.second ? elem1.second > elem2.second:elem1.first < elem2.first;
            };

    // Declaring a set that will store the pairs using above comparision logic
    std::set<std::pair<std::string, int>, Comparator> tempSet(
            cm.begin(), cm.end(), compFunctor);
    std::set<std::pair<std::string, int>, Comparator> cSet(
            cm.begin(), cm.end(), compFunctorDesc);

    for(int i = 0; i<size;i++) {
       int j=0;
        std::pair<std::string, int> e1;
        std::pair<std::string, int> e2;
        std::pair<std::string, int> rr;
        for (std::pair<std::string, int> element : tempSet) {
            if(j==0) e1 = element;
            if(j==1) {
                e2 = element;
                rr = std::make_pair(e1.first + element.first,e1.second + element.second );

            }
            ++j;
        }
        tempSet.erase(e1);
        tempSet.erase(e2);
        tempSet.insert(rr);
        cSet.insert(rr);

    }
    vector<string> sc;
    for (std::pair<std::string, int> element : cSet)
        sc.push_back(element.first);

    for(int i = 0; i<sc.size();i++)
        cout<<sc[i]<<" ";

    cout<<endl;
    cout<<"Fillin tree"<<flush;
    tree = fillTree(sc);

    cout<<"tree filled"<<endl<<flush;
    map<char, string> mp;
    codeTree(tree,"",mp);
    for (itr = mp.begin(); itr != mp.end(); ++itr) {
        cout << '\t' << itr->first
             << '\t' << itr->second << '\n';
    }
    string result;
    for(int i =0 ;i<text.length();i++){
        result.append(mp.at((char)text[i]));
    }
    cout<<result<<flush;
    return  result;
}

string decodeSign(node* tree ,string text){
     node* n = tree;
     for(int i =0;i<text.length();i++){
         if(((char)text[i]) == '0')
             n = n->left;
         else if(((char)text[i]) == '1')
             n = n->right;
     }
     if(n->i.length()==1)
         return n->i;
     return "";
 }
string decode(node* tree ,string text){
     string result = "";
     string act = "";
    for(char & i : text) {
        act += i;
        string ds = decodeSign(tree,act);
        if(ds.length()==1){
            act = "";
            result+=ds;
        }
    }
    return result;
    }

int main(){
    node * tree;
    ifstream fp;
//    fp.open(R"(C:\Users\Aspire\IdeaProjects\Algorytmy-Scala\C++\text)");
//    if (!fp) {
//        cout << "Error, file couldn't be opened" << endl;
//        return 1;
//    }
    std::ifstream t(R"(C:\Users\Aspire\IdeaProjects\Algorytmy-Scala\C++\text)");

    std::stringstream buffer;
    buffer << t.rdbuf();
    string text = buffer.str();
    string res = code(text,tree);
    cout<<res<<endl;
    std::ofstream out(R"(C:\Users\Aspire\IdeaProjects\Algorytmy-Scala\C++\crypted)");
    out << res;
    out.close();
    string dec = decode(tree,res);
    cout<<dec;
    std::ofstream out2(R"(C:\Users\Aspire\IdeaProjects\Algorytmy-Scala\C++\decrypted)");
    out2 << dec;
    out2.close();

}