package server.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import server.server.fileSystem.service.StorageService;
import server.server.fileSystem.utilities.FolderUtility;
import server.server.service.TopPerformersService;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@SpringBootApplication
public class Main {
    @Autowired
    static TopPerformersService topPerformersService;
    public static void main(String[] args) throws IOException {
//        byte[] bytes = "iVBORw0KGgoAAAANSUhEUgAAANMAAAAoCAYAAABkUs8DAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAAsMSURBVHhe7ZzvT1PJGsfvP3M3IfJiIyaavlFeXDQ3sYkhJprwQsMLcZM1mqwJiYQXEF+sIZGkiSxoV7hUWZXQpWqVBSxedqvWhbVapdZlARfWrlSL1KpFk++dmXPm9LSd0tPTQxf2zotPSOeZMz/OzHfmmedM+MfKp0+QSCSlI8UkkViEFJNEYhFSTBKJRUgxSSQWIcUkkViEFJNEYhFSTBKJRUgxSSQWIcUkkViEFJPkb8/Dx2GGyGYlUkySDc3bd+8xN/8HPqx8FNqfPvsNZ7p6Gb/+NifM82FlhZVByxLZjSLFJNnQ9H9/gwll9LZfaF+MvUbHWRc6nRfx6nVcmOeHWz+yMtyeIaHdKFJMkg3N2fN9TAj9bq/QTnesC5cG8f3V/EKhdlrGt/+5LLQbRYpJsgZMoqexCY1tQ1gQ2q2DunFUUF3f9mEqMp1jD0wEmVAoU08z7UtvljF4bZjZzp7/Lq8baJR1KqY4gsMeuAd8CL4W2ctPuPMgbFu2oa4zIrSbYcZP++jB+JzAHnaizrYNVXVOhNU02oaqLdstbcPaMISjn23CP+0dWtvXEu/QmCaYsfG7WH6bZOkvY6+Yizfi+4nsTD/A2XMJbxJvmS30JMJESO3fnLvAXD19mWZYp2KKwGEng/FZLRxhkb38jLdsJ+3ZBFuL2Dc3g/cY7eMmHL0usPtbYaMT0taKcTVtpNn6NqwN5RXTxIMQE8XDUJgJ5CzZpX66O8FcP+q6JZLv8Cq+xM5N127cwlXvKBNe32UP5l/8yZ598PCJsOxikGIyTBKx+TgSQps5VhUTIbEYRSypT7O+DWtDecX0fOEFE0d0MYbXS29wfcin7VRPnv6q5fv5l0csjYrnzv1fWBSPP7sQfZlRphksERMd9IV5irGBTkTV/FFlO86lODEp9QvqXubtiiKRyrJZTTKu1ZUpgPwUElOxFDsOHG08FvONRxpjdZgRE10o1LLzzgsxdOdhwgk/09Lm5l9gMvg4I1/q40fcn3yIP/5c1NLorvbNORcTlj6vGUyIKf2ixv0dqP/XZjYhND6vxVFXSPCikwgPNMH+uS4vZUstGgciWn4+wUTYHcpZIeyoZb8Pdw+h2c7rPw6vWkbM78RRLV2lYhvsJzwI84keOIWdNH2fEzPqcxmkfGjcSp9rQN9iuk7eBo1FPxyHalCpr4tQtacV3qgunw5elpBjatQp3AE7/a2bkPy5bPEt/Fc8Do2Dc3Cz96lflLLGr1r3DKHSLm632ToyxBQifaog6bYj6Jvm6XGMdx4Xzovmm360sUU1Pbb5ON97BT/e+VloW42bw7dxeeC60FYs5sVUsRmV5MVU2pvQ5QuRFSWCwEC7Njg7TgwhpnsuqE4Emr8nEGErUNDnROMuJb/dEWL5lEM5EQMrpwZHO5VDOmUkpHwn0CYjHZjq/Wh2UrufiSJ2/bhy1qAD7fQhSOoJBzxoq69RnqlugpeIY+XTHLr20Tp2o20i3U5O7PIRlr/qhI/9FoppkbwLGy1jMxGqC4EIWVUjk3C3HVTbsB9dgp01FvKx/pxk9W/C3pPpPrr9akTJoJgK9pe+I9FEZ+lknOrb4abjERyC48vdyjO7TiGg28lN16EXExHSXioY8k4cIZ4vDm8jH5cGtA1MIsznBV8MWdmFxTQweJOch0aFttW4SM5Nt27fEdqKxbyYKIdcWMh2n1IhOPZQ+3Y0+9Q0vgvs6UBQkF9ZfcjE06Jaq7t5mpi2HoGbCUPl9RUcpi+/gjynDVgaLmhbsyKQbMGkyRVarpji6PuC5tmEeldUTUvDJ2Al2WnyuUOrunlGxLR4BfW0v/Q95OwmSQRPq+9JNNEJNtI2/YLH+nQoq02l1MHbPk3GhS462ePiU4MsonlBhXZMCbgYEdPQ6Di+u+IR2vLxPpVSAhcWXTUqQUz6yZ/FcBOqaB7VZRlp3kby1+BkQJCXEOtrYC+trptPSmNiqlJFwVnoPsjS93bm+V6Q8qOZ7ngVZHDo4GmuHHE79CH4iXZF/HUuzQXMEdO8C3U0z652BPhzGZAzQAHfv1Qx8f7muJ4c3l+hmMTjx8eCl1lSHbTtfPcmQmoLZr4PZV5sQ+NwOi2DRRfqWVsLi8l/bxLO7ktCWz6iL2PsrEWDECJ7sZgX07/bERTaCSkPDvOX+SmKnjr6QmpQ91UTGunHvGwO1bIzx46vJ9UyjIkpLT6FQqKlKBM4Xe5M535Wll6AIydyBzlHTGRVpQtGtqCLoVQx8cmoeQACsvurjV81ceWy8jLIjqrvZ2l11MLO3GBROJ+MMfNgyJk077dEPg8Ki4mGxakw3n34ILSL4Pf2lpYTQnuxlEFM/KWRsw3xT7WzgQh+XjAopuxJaEZMmmvId5js3yp/OzHpys3ASjFRKmqwg+1c20m79XfjuEtJnovwtGwmcZI9W1hM07PPmTDoXTyRXQQNldNvTyKbGdaFm5eLOTEVdPP44HA3T03X70SinYqSIybu5uWblMTNSyyL0tOUKibDbm0JYiqpDi4gnaunPzMF2pSAR96y+VnbgJheqC7b7O8LQrsI6hp2u/qFNjOUFICwHfMYC0CQVZwdNOucmMk5aCYx3kJe6pbdJH/ap3Z/ScvYjTZBICGfmLRdRXhYzg1AaPAz0hetaKbRxewzFCFHTAUCEAmyoNA+V32VPwARPK1MpsODAnvJAYh0f0sRk2V1aGFxIgweNJojCxJNE5ynVqIeNVJKKSwm/q2pWC5cHhSWZwbzYtKFxrVQ9/UOHFZD3ZmRonQIVJ9/IeJDlxqOrdyXGdHhO4TtQDt6iAvY4ziFPnWw8oqJIArjZoR9tdC4Hh69U9ipnd3S5IqJIAqNz4fgbWvADnWSKCtxnCwOSqjXfkb3PN/BdzWhi7q5zlNw+FRXyIiYCBn9dSmh5Yz+0naUIiaCVXVo5eiid7HhVuxkz2+GbW+Ddoauomk2cuYy6OZRHk89w937D4pi5vm8sCwzmBcTeVGij37UR653+LNCrpQkgi7Bxzn1Y2rOjkV2uB7+HYOxGXXdijuwmpgoMdHHZDrhj7kQzHc7gU/sPO6rUEwU+tE2o50KVXua4NY+TEbRc0BJ33laL9Q4RlqU4At/ztai7poGxURZuNkK+5Z0GYzPyTh0hox/UOUIxESxqg7NO9AvtlE/uhoPYmc1PV9R9qP+aw/CSeMBiPVASWLiL0q7jkIofG1Hd23ESH56TafI6yWc9NUX41d8TKO7TiS+lrNKqDyVJM8VvgJUaBEpbhzMUY460vjQTD9dbG3FiNC+vrBETJLyUEhMG48kwuRo0NxyRfDRlngYg0cyAlnrHSmm9Q7ZtRJ0V9VuiujdqY2P5vYd6MDIHN+5k5jxnVICFvTcuUH6K8W0zuE3Eji5V4A2OnGMtx1UAg66fjKqG9CVHeVbx5gQk3rmMXmOkRTJ8hwCN5WP2t7AXMFz1YZF10/KSLAcZzJrMSEmiUQiQopJIrEIKSaJxCL+j8S0hNmpICKxlMBmlswy30ef4sHUcyzl5CsP8+P9ONN1Dffeiu2meP8K06SPD+77MXaH/H34FLPLVr5Di4lN68YgirG+XpwZmCzLWfOvFdMj+t84L2FoVmCzmpf30EvvY7mD5l6sqK1ZZT7y0vtelzD6XJenjFgqpvdRTNwg5Z27iF73KMbuUyFNYuyGB91O0s8LY3gUX3+iyhwDKaa1410CiRVBuhHytTWjzBSW/sJV2zIxLYdwtbsXnf1+TIl28pUlRHxuIrR+jM6uN0Hpx6CcYvqE/wEhoqaHqoIDrQAAAABJRU5ErkJggg==".getBytes();
//        BufferedImage bufferedImage = FolderUtility.convertByteArrayToImage(bytes);
//
//        displayImage(bufferedImage);
    }


}