--------------------
---Estas lineas solo se hacen una vez, luego comentar
--------------------
--------------------
--create database carta;

--create user 'tw'@localhost identified by 'tw2122';
--GRANT ALL PRIVILEGES on carta.* TO 'tw'@localhost;
--flush privileges;

--------------------
--------------------
--------------------

--
-- 

use carta;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carta`
--

CREATE TABLE IF NOT EXISTS `carta` (
  `idCliente` int(11) NOT NULL,
  `idProducto` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  PRIMARY KEY (`idCliente`,`idProducto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE IF NOT EXISTS `productos` (
  `idProductos` int(11) NOT NULL AUTO_INCREMENT,
  `nombreProd` varchar(50) NOT NULL,
  `pathImagen` varchar(100) NOT NULL,
  `comentarios` varchar(100) NOT NULL,
  PRIMARY KEY (`idProductos`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`idProductos`, `nombreProd`, `pathImagen`, `comentarios`) VALUES
(1, 'PS3', 'ps3.png', 'Color Negro y con disco duro de 8GB'),
(2, 'Bicicleta', 'bici.jpg', 'Color rojo'),
(3, 'Head first servlets and JSP', 'servlet.jpg', 'Libro publicado por Oreilly'),
(4, 'Notebook asus 1000h', 'asus1000h.jpg', 'Notebook de 10 pulgadas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) DEFAULT NULL,
  `apellidos` varchar(50) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `username` varchar(10) DEFAULT NULL,
  `password` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

