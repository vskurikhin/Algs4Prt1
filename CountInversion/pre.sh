#!/bin/sh
COMMENT=false
myperl() {
    if $COMMENT ; then
        echo "Commenting $F"
        perl -pi.bak -e 's|^(/\*[ \t]*_if_[ \t]*)\*/$|\1|' $1
        perl -pi -e 's|^/\*([ \t]*_endif_[ \t]*\*/)$|\1|' $1
        perl -pi -e 's|^(/\*[ \t]*_unless_[ \t]*)$|\1*/|' $1
        perl -pi -e 's|^([ \t]*_endunless_[ \t]*\*/)$|/*\1|' $1
    else
        echo "UnCommenting $F"
        perl -pi.bak -e 's|^(/\*[ \t]*_if_[ \t]*)$|\1*/|' $1
        perl -pi -e 's|^([ \t]*_endif_[ \t]*\*/)$|/*\1|' $1
        perl -pi -e 's|^(/\*[ \t]*_unless_[ \t]*)\*/$|\1|' $1
        perl -pi -e 's|^/\*([ \t]*_endunless_[ \t]*\*/)$|\1|' $1
    fi
}
if [ ! "x-c" != "x$1" ] ; then
    COMMENT=true
    shift
fi
if [ "x" != "x$1" ] ; then
    for F in $* ; do
      myperl $F
    done
else
    for F in src/*.java ; do
      myperl $F
    done
fi
