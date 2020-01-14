package com.pcz.cart;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
public class CartTest {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private RedisTemplate<String, Product> redisTemplate;

    @After
    public void cleanUp() {
        redisTemplate.delete("9781617291203");
        redisTemplate.delete("cart");
        redisTemplate.delete("cart1");
        redisTemplate.delete("cart2");
    }

    @Test
    public void workingWithSimpleValues() {
        Product product = new Product();
        product.setSku("9781617291203");
        product.setName("Spring in Action");
        product.setPrice(39.99f);

        redisTemplate.opsForValue().set(product.getSku(), product);
        Product found = redisTemplate.opsForValue().get(product.getSku());
        Assert.assertEquals(product.getSku(), found.getSku());
        Assert.assertEquals(product.getName(), found.getName());
        Assert.assertEquals(product.getSku(), found.getSku());
    }

    @Test
    public void workingWithLists() {
        Product product = new Product();
        product.setSku("9781617291203");
        product.setName("Spring in Action");
        product.setPrice(39.99f);

        Product product2 = new Product();
        product2.setSku("9781935182436");
        product2.setName("Spring Integration in Action");
        product2.setPrice(49.99f);

        Product product3 = new Product();
        product3.setSku("9781935182955");
        product3.setName("Spring Batch in Action");
        product3.setPrice(49.99f);

        redisTemplate.opsForList().rightPush("cart", product);
        redisTemplate.opsForList().rightPush("cart", product2);
        redisTemplate.opsForList().rightPush("cart", product3);
        Assert.assertEquals(3, redisTemplate.opsForList().size("cart").longValue());

        Product first = redisTemplate.opsForList().leftPop("cart");
        Product last = redisTemplate.opsForList().rightPop("cart");

        Assert.assertEquals(product.getSku(), first.getSku());
        Assert.assertEquals(product.getName(), first.getName());
        Assert.assertEquals(product.getPrice(), first.getPrice(), 0.005);

        Assert.assertEquals(product3.getSku(), last.getSku());
        Assert.assertEquals(product3.getName(), last.getName());
        Assert.assertEquals(product3.getPrice(), last.getPrice(), 0.005);

        Assert.assertEquals(1, redisTemplate.opsForList().size("cart").longValue());
    }

    @Test
    public void workingWithListsRange() {
        for (int i = 0; i < 30; i++) {
            Product product = new Product();
            product.setSku("SKU-" + i);
            product.setName("PRODUCT " + i);
            product.setPrice(i + 0.99f);
            redisTemplate.opsForList().rightPush("cart", product);
        }
        Assert.assertEquals(30, redisTemplate.opsForList().size("cart").longValue());

        List<Product> products = redisTemplate.opsForList().range("cart", 2, 12);
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            Assert.assertEquals("SKU-" + (i + 2), product.getSku());
            Assert.assertEquals("PRODUCT " + (i + 2), product.getName());
            Assert.assertEquals(i + 2 + 0.99f, product.getPrice(), 0.005);
        }
    }

    @Test
    public void performingOperationsOnSets() {
        Product product = new Product();
        product.setSku("9781617291203");
        product.setName("Spring in Action");
        product.setPrice(39.99f);

        redisTemplate.opsForSet().add("cart", product);
        Assert.assertEquals(1, redisTemplate.opsForSet().size("cart").longValue());
    }

    @Test
    public void performingOperationsOnSetsSetOperations() {
        for (int i = 0; i < 30; i++) {
            Product product = new Product();
            product.setSku("SKU-" + i);
            product.setName("PRODUCT " + i);
            product.setPrice(i + 0.99f);
            redisTemplate.opsForSet().add("cart1", product);
            if (i % 3 == 0) {
                redisTemplate.opsForSet().add("cart2", product);
            }
        }

        Set<Product> diff = redisTemplate.opsForSet().difference("cart1", "cart2");
        Set<Product> union = redisTemplate.opsForSet().union("cart1", "cart2");
        Set<Product> isect = redisTemplate.opsForSet().intersect("cart1", "cart2");

        Assert.assertEquals(20, diff.size());
        Assert.assertEquals(30, union.size());
        Assert.assertEquals(10, isect.size());

        Product random = redisTemplate.opsForSet().randomMember("cart1");
        Assert.assertNotNull(random);
    }

    @Test
    public void bindingToAKey() {
        Product product = new Product();
        product.setSku("9781617291203");
        product.setName("Spring in Action");
        product.setPrice(39.99f);

        Product product2 = new Product();
        product2.setSku("9781935182436");
        product2.setName("Spring Integration in Action");
        product2.setPrice(49.99f);

        Product product3 = new Product();
        product3.setSku("9781935182955");
        product3.setName("Spring Batch in Action");
        product3.setPrice(49.99f);

        BoundListOperations<String, Product> cart = redisTemplate.boundListOps("cart");
        cart.rightPush(product);
        cart.rightPush(product2);
        cart.rightPush(product3);
        Assert.assertEquals(3, cart.size().longValue());

        Product first = cart.leftPop();
        Product last = cart.rightPop();
        Assert.assertEquals(product.getSku(), first.getSku());
        Assert.assertEquals(product.getName(), first.getName());
        Assert.assertEquals(product.getPrice(), first.getPrice(), 0.005);

        Assert.assertEquals(product3.getSku(), last.getSku());
        Assert.assertEquals(product3.getName(), last.getName());
        Assert.assertEquals(product3.getPrice(), last.getPrice(), 0.005);

        Assert.assertEquals(1, cart.size().longValue());
    }

    @Test
    public void settingKeyAndValueSerializers() {
        RedisTemplate<String, Product> redis = new RedisTemplate<>();
        redis.setConnectionFactory(redisConnectionFactory);
        redis.setKeySerializer(new StringRedisSerializer());
        redis.setValueSerializer(new Jackson2JsonRedisSerializer<>(Product.class));
        redis.afterPropertiesSet();

        Product product = new Product();
        product.setSku("9781617291203");
        product.setName("Spring in Action");
        product.setPrice(39.99f);

        redis.opsForValue().set(product.getSku(), product);
        Product found = redis.opsForValue().get(product.getSku());
        Assert.assertEquals(product.getSku(), found.getSku());
        Assert.assertEquals(product.getName(), found.getName());
        Assert.assertEquals(product.getPrice(), found.getPrice(), 0.005);

        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory);
        String json = stringRedisTemplate.opsForValue().get(product.getSku());
        Assert.assertEquals("{\"sku\":\"9781617291203\",\"name\":\"Spring in Action\",\"price\":39.99}", json);
    }
}
