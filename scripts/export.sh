NAME=ussr-src-`head -1 VERSION.txt`.zip
pushd ..
rm -f ussr/${NAME}
zip -r ussr/${NAME} ussr/README.txt ussr/HOWTO.txt ussr/INSTALL.pdf ussr/VERSION.txt ussr/bin/com ussr/bin/org ussr/bin/ussr ussr/doc ussr/resources ussr/src ussr/zoo -x '*.svn*'
popd
