
# my-collection
1: git init <br>
2: git remote add origin https://github.com/axuplus/my-collection.git <br>
3: git add * <br>
4: git commit -m "" <br>
// 拉取远程分支出现fatal: refusing to merge unrelated histories </br>
5: git pull origin master <br>
// 消除远程跟本地差异 </br>
6: git pull origin master --allow-unrelated-histories <br>
// push的时候带参下次就不会出现The current branch master has no upstream branch. </br>
7: git push --set-upstream origin master <br>
