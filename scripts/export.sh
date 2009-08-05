#!/bin/sh
NAME=ussr-src-`head -1 VERSION.txt`.zip
echo "Warning: temporarily renaming .classpath file"
mv -f .classpath .classpath-export
cat .classpath-export | grep -v home/ | grep -v jmf.jar | grep -v sandbox > .classpath
pushd ..
rm -f ussr/${NAME}
zip -r ussr/${NAME} ussr/{.classpath,.project} ussr/README.html ussr/README.txt ussr/doc ussr/LICENSE.txt ussr/VERSION.txt ussr/bin/{com,org,ussr} ussr/resources ussr/src ussr/zoo -x '*.svn*' -x '*~'
popd
mv -f .classpath-export .classpath
echo "Restored .classpath"
