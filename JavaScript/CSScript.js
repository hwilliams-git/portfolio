// This is a script I created to use JavaScript in place of CSS for the test websites I create.
// There is still more I want to add, but that will come as I find more thisng I want to put into
// websites.

var screenWidth = screen.width;
var screenHeight = screen.height; 

function SetIdStyle(_idName, _style, _input)
{
    id = document.getElementById(_idName); console.log('here');
    StyleOptions(id, _style, _input); 
}

function GetClassInID(_idName, _className)
{
    return document.getElementById(_idName).getElementsByClassName(_className);
}

function SetClassStyleInId(_idName, _className, _style, _input)
{
    classArray = GetClassInID(_idName, _className);
	
    for(i = 0;i < classArray.length;i++)
    {
        StyleOptions(classArray[i], _style, _input);
    }
}

function SetClassStyleAll(_className, _style, _input)
{
    classArray = document.getElementsByClassName(_className);
	
    for(i = 0;i < classArray.length;i++)
    {
        StyleOptions(classArray[i], _style, _input);
    }
}

function StyleOptions(_element, _style, _input)
{
    switch(_style)
    {
        case('width'):
            _element.style.width = _input;
            break;
        case('height'):
            _element.style.height = _input;
            break;
        case('display'):
            _element.style.display = _input;
            break;
        case('float'):
            _element.style.cssFloat = _input;
            break;
        case('position'):
            _element.style.position = _input;
            break;
        case('zIndex'):
            _element.style.zIndex = _input;
            break;
        case('top'):
            _element.style.top = _input;
            break;
        case('bottom'):
            _element.style.bottom = _input;
            break;
        case('left'):
            _element.style.left = _input;
            break;
        case('right'):
            _element.style.right = _input;
            break;
        case('transform'):
            _element.style.transform = _input;
            break;
        case('margin'):
            _element.style.margin = _input;
            break;
        case('padding'):
            _element.style.padding = _input;
            break;
        case('overflow'):
            _element.style.overflow = _input;
            break;
        case('border'):
            _element.style.border = _input; 
            break;
        case('borderRadius'):
            _element.style.borderRadius = _input; 
            break;
        case('backgroundColor'):
            _element.style.backgroundColor = _input;
            break;
        case('backgroundImage'):
            _element.style.backgroundImage = _input;
            break;
        case('backgroundRepeat'):
            _element.style.backgroundRepeat = _input;
            break;
        case('backgroundAttachment'):
            _element.style.backgroundAttachment = _input;
            break;
        case('backgroundSize'):
            _element.style.backgroundSize = _input;
            break;
        case('fontSize'):
            _element.style.fontSize = _input;
            break;
        case('verticalAlign'):
            _element.style.verticalAlign = _input;
            break;
        case('textAlign'):
            _element.style.textAlign = _input;
            break;
        case('textColor'):
            //Font color
            _element.style.color = _input;
            break;
        case('textDecoration'):
            _element.style.textDecoration = _input;
            break;
        default:
            break;
    }
}
