function fixMath(m, n, op) {
  if(typeof(m) == "undefined"){
    return 0;
  }
  var a = (m+"");
  var b = (n+"");
  var x = 1;
  var y = 1;
  var c = 1;
  if(a.indexOf(".")>0) {
    x = Math.pow(10, a.length - a.indexOf(".") - 1);
  }
  if(b.indexOf(".")>0) {
    y = Math.pow(10, b.length - b.indexOf(".") - 1);
  }
  switch(op)
  {
    case '+':
    case '-':
      c = Math.max(x,y);
      m = Math.round(m*c);
      n = Math.round(n*c);
      break;
    case '*':
      c = x*y
      m = Math.round(m*x);
      n = Math.round(n*y);
      break;
    case '/':
      c = Math.max(x,y);
      m = Math.round(m*c);
      n = Math.round(n*c);
      c = 1;
      break;
  }
  return eval("("+m+op+n+")/"+c);
}