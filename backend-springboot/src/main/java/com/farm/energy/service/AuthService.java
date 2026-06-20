// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： AuthService
 * - 层级：服务层
 * - 职责：实现领域业务逻辑、数据聚合、校验与持久化编排。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 AuthService 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.service;

// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.LoginRequest;
// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.UserCreateRequest;
// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.UserUpdateRequest;
// 行注释：引入数据库实体类，用于读取或保存业务表记录。
import com.farm.energy.entity.SysUser;
// 行注释：引入数据库实体类，用于读取或保存业务表记录。
import com.farm.energy.entity.UserSession;
// 行注释：引入数据访问接口，用于操作对应数据库表。
import com.farm.energy.repository.SysUserRepository;
// 行注释：引入数据访问接口，用于操作对应数据库表。
import com.farm.energy.repository.UserSessionRepository;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.beans.factory.annotation.Value;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.http.HttpStatus;
// 行注释：引入鉴权上下文或安全工具，用于识别当前登录用户。
import org.springframework.security.crypto.password.PasswordEncoder;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.stereotype.Service;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.transaction.annotation.Transactional;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.web.server.ResponseStatusException;

// 行注释：引入时间类型，用于记录日期、创建时间或预测日期。
import java.time.LocalDateTime;
// 行注释：引入 Map 结构，用于组装接口返回的键值数据。
import java.util.LinkedHashMap;
// 行注释：引入 List 结构，用于返回表格或图表列表数据。
import java.util.List;
// 行注释：引入 Map 结构，用于组装接口返回的键值数据。
import java.util.Map;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import java.util.Optional;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import java.util.UUID;

// 行注释：把当前类标记为业务服务层，专门处理模块业务逻辑。
@Service
// 行注释：定义 AuthService 业务服务，承载该模块的主要代码。
// 类注释：这是 认证登录 模块的业务服务类，负责处理核心业务逻辑、数据校验、统计计算和数据库读写编排。
public class AuthService {

    // 行注释：声明字段 ROLES，保存当前对象需要的数据或依赖。
    private static final List<String> ROLES = List.of("admin", "manager", "viewer");
    // 行注释：声明字段 USER_STATUSES，保存当前对象需要的数据或依赖。
    private static final List<String> USER_STATUSES = List.of("active", "disabled");

    // 行注释：注入仓储接口，用于访问对应数据库表。
    private final SysUserRepository sysUserRepository;
    // 行注释：注入仓储接口，用于访问对应数据库表。
    private final UserSessionRepository userSessionRepository;
    // 行注释：声明字段 passwordEncoder，保存当前对象需要的数据或依赖。
    private final PasswordEncoder passwordEncoder;

    // 行注释：读取配置文件或环境变量中的值，作为运行参数使用。
    @Value("${app.auth.session-hours:12}")
    // 行注释：声明字段 sessionHours，保存当前对象需要的数据或依赖。
    private int sessionHours;

    // 行注释：声明 AuthService 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：Spring 创建 AuthService 对象时调用这个构造方法，用来注入本类需要的依赖。
    public AuthService(SysUserRepository sysUserRepository,
                       // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                       UserSessionRepository userSessionRepository,
                       // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                       PasswordEncoder passwordEncoder) {
        // 行注释：把构造方法传入的 sysUserRepository 保存到成员变量，后续方法会继续调用它。
        this.sysUserRepository = sysUserRepository;
        // 行注释：把构造方法传入的 userSessionRepository 保存到成员变量，后续方法会继续调用它。
        this.userSessionRepository = userSessionRepository;
        // 行注释：把构造方法传入的 passwordEncoder 保存到成员变量，后续方法会继续调用它。
        this.passwordEncoder = passwordEncoder;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： login
     * - 作用：校验用户凭据，创建会话令牌，并返回当前用户信息。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 login 方法，完成 AuthService 中对应的一步业务处理。
    // 方法用法：AuthController 调用它处理登录流程，负责校验密码、生成 token、保存会话和返回用户信息。
    public Map<String, Object> login(LoginRequest request) {
        // 行注释：声明变量 username，保存本行计算或查询得到的结果，供后续逻辑使用。
        String username = request.getUsername() == null ? "" : request.getUsername().trim();
        // 行注释：声明变量 password，保存本行计算或查询得到的结果，供后续逻辑使用。
        String password = request.getPassword() == null ? "" : request.getPassword();
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (username.isBlank() || password.isBlank()) {
            // 行注释：抛出明确异常，让前端收到规范的错误提示。
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username and password are required");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：声明变量 user，保存本行计算或查询得到的结果，供后续逻辑使用。
        SysUser user = sysUserRepository.findByUsername(username)
                // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));

        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (!"active".equalsIgnoreCase(user.getStatus())) {
            // 行注释：抛出明确异常，让前端收到规范的错误提示。
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account is disabled");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            // 行注释：抛出明确异常，让前端收到规范的错误提示。
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：调用 JPA 删除数据或清理过期记录。
        userSessionRepository.deleteByExpiresAtBefore(LocalDateTime.now());
        // 行注释：调用 JPA 删除数据或清理过期记录。
        userSessionRepository.deleteByUser(user);

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        UserSession session = new UserSession();
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        session.setUser(user);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        session.setToken(UUID.randomUUID().toString().replace("-", ""));
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        session.setExpiresAt(LocalDateTime.now().plusHours(sessionHours));
        // 行注释：调用 JPA 保存实体，把数据写入数据库。
        session = userSessionRepository.save(session);

        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        user.setLastLoginAt(LocalDateTime.now());
        // 行注释：调用 JPA 保存实体，把数据写入数据库。
        sysUserRepository.save(user);

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> payload = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        payload.put("token", session.getToken());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        payload.put("expires_at", session.getExpiresAt());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        payload.put("user", toUserMap(user));
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        payload.put("message", "Login successful");
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return payload;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： logout
     * - 作用：使令牌会话失效，要求客户端重新认证。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 logout 方法，完成 AuthService 中对应的一步业务处理。
    // 方法用法：退出登录接口调用它删除 token 会话，使该 token 后续不能再访问接口。
    public void logout(String token) {
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (token != null && !token.isBlank()) {
            // 行注释：调用 JPA 删除数据或清理过期记录。
            userSessionRepository.deleteByToken(token);
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： resolveValidSession
     * - 作用：解析并校验令牌会话，包括过期与用户状态检查。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 resolveValidSession 方法，完成 AuthService 中对应的一步业务处理。
    // 方法用法：鉴权拦截器每次请求都会调用它，根据 token 查找有效会话并过滤过期用户。
    public Optional<UserSession> resolveValidSession(String token) {
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (token == null || token.isBlank()) {
            // 行注释：返回处理结果，最终会交给前端或上层方法使用。
            return Optional.empty();
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：调用 JPA 删除数据或清理过期记录。
        userSessionRepository.deleteByExpiresAtBefore(LocalDateTime.now());
        // 行注释：声明变量 optional，保存本行计算或查询得到的结果，供后续逻辑使用。
        Optional<UserSession> optional = userSessionRepository.findByToken(token)
                // 行注释：筛选列表数据，只保留当前业务需要的记录。
                .filter(it -> it.getExpiresAt().isAfter(LocalDateTime.now()))
                // 行注释：筛选列表数据，只保留当前业务需要的记录。
                .filter(it -> "active".equalsIgnoreCase(it.getUser().getStatus()));
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return optional;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： requireUserById
     * - 作用：加载必需实体，未找到时抛出明确异常。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 requireUserById 方法，完成 AuthService 中对应的一步业务处理。
    // 方法用法：业务处理前调用它确认目标记录存在，不存在时抛出明确错误。
    public SysUser requireUserById(Long id) {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return sysUserRepository.findById(id)
                // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getCurrentUserProfile
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getCurrentUserProfile 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getCurrentUserProfile，负责从数据库聚合并整理 Auth 模块结果。
    public Map<String, Object> getCurrentUserProfile(SysUser user) {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return toUserMap(user);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： listUsers
     * - 作用：查询并返回集合数据，用于界面表格/图表渲染。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 listUsers 方法，完成 AuthService 中对应的一步业务处理。
    // 方法用法：控制器加载列表时调用 listUsers，负责查询并转换成前端表格需要的数据结构。
    public List<Map<String, Object>> listUsers() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return sysUserRepository.findAll().stream().map(this::toUserMap).toList();
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getUser
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getUser 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getUser，负责从数据库聚合并整理 Auth 模块结果。
    public Map<String, Object> getUser(Long id) {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return toUserMap(requireUser(id));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： createUser
     * - 作用：校验请求参数并持久化新增业务记录。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 createUser 新增方法，把前端提交的数据保存到数据库。
    // 方法用法：控制器新增数据时调用 createUser，负责校验参数并保存新记录。
    public Map<String, Object> createUser(UserCreateRequest request) {
        // 行注释：声明变量 username，保存本行计算或查询得到的结果，供后续逻辑使用。
        String username = request.getUsername() == null ? "" : request.getUsername().trim();
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (username.isBlank()) {
            // 行注释：抛出明确异常，让前端收到规范的错误提示。
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is required");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getPassword() == null || request.getPassword().length() < 6) {
            // 行注释：抛出明确异常，让前端收到规范的错误提示。
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 6 chars");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (sysUserRepository.findByUsername(username).isPresent()) {
            // 行注释：抛出明确异常，让前端收到规范的错误提示。
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        SysUser user = new SysUser();
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        user.setUsername(username);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        user.setRole(normalizeRole(request.getRole()));
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        user.setDisplayName(request.getDisplayName() == null || request.getDisplayName().isBlank()
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                ? username
                // 行注释：读取对象字段值，参与业务判断、计算或返回。
                : request.getDisplayName().trim());
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        user.setStatus("active");
        // 行注释：调用 JPA 保存实体，把数据写入数据库。
        return toUserMap(sysUserRepository.save(user));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： updateUser
     * - 作用：在输入规范化后，对现有记录执行部分字段更新。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 updateUser 更新方法，修改数据库中的已有记录。
    // 方法用法：控制器修改数据时调用 updateUser，负责找到原记录、更新字段并保存。
    public Map<String, Object> updateUser(Long id, UserUpdateRequest request) {
        // 行注释：声明变量 user，保存本行计算或查询得到的结果，供后续逻辑使用。
        SysUser user = requireUser(id);

        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getDisplayName() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            user.setDisplayName(request.getDisplayName().trim());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getRole() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            user.setRole(normalizeRole(request.getRole()));
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getStatus() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            user.setStatus(normalizeStatus(request.getStatus()));
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
            if (request.getPassword().length() < 6) {
                // 行注释：抛出明确异常，让前端收到规范的错误提示。
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 6 chars");
            // 行注释：结束当前代码块，表示这段逻辑处理完成。
            }
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
            // 行注释：调用 JPA 删除数据或清理过期记录。
            userSessionRepository.deleteByUser(user);
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：调用 JPA 保存实体，把数据写入数据库。
        return toUserMap(sysUserRepository.save(user));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： changeOwnPassword
     * - 作用：校验旧密码并更新当前用户的密码哈希。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 changeOwnPassword 方法，完成 AuthService 中对应的一步业务处理。
    // 方法用法：修改密码接口调用它校验旧密码并写入新密码，保证只有本人能改自己的密码。
    public void changeOwnPassword(Long userId, String oldPassword, String newPassword) {
        // 行注释：声明变量 user，保存本行计算或查询得到的结果，供后续逻辑使用。
        SysUser user = requireUser(userId);
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (oldPassword == null || !passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            // 行注释：抛出明确异常，让前端收到规范的错误提示。
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password is incorrect");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (newPassword == null || newPassword.length() < 6) {
            // 行注释：抛出明确异常，让前端收到规范的错误提示。
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password must be at least 6 chars");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        // 行注释：调用 JPA 保存实体，把数据写入数据库。
        sysUserRepository.save(user);
        // 行注释：调用 JPA 删除数据或清理过期记录。
        userSessionRepository.deleteByUser(user);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： deleteUser
     * - 作用：删除或逻辑移除目标记录，并维护数据一致性。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 deleteUser 删除方法，删除前端指定的数据记录。
    // 方法用法：控制器删除数据时调用 deleteUser，负责校验记录存在并执行删除。
    public void deleteUser(Long id, Long currentUserId) {
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (id.equals(currentUserId)) {
            // 行注释：抛出明确异常，让前端收到规范的错误提示。
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete current login user");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：声明变量 user，保存本行计算或查询得到的结果，供后续逻辑使用。
        SysUser user = requireUser(id);
        // 行注释：调用 JPA 删除数据或清理过期记录。
        userSessionRepository.deleteByUser(user);
        // 行注释：调用 JPA 删除数据或清理过期记录。
        sysUserRepository.delete(user);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： ensureUser
     * - 作用：确保默认账号存在，并在需要时修正其角色、状态与密码。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 ensureUser 方法，完成 AuthService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 ensureUser，用于完成 Auth 模块的一段核心业务逻辑。
    public SysUser ensureUser(String username, String displayName, String role, String rawPassword) {
        // 行注释：声明变量 existing，保存本行计算或查询得到的结果，供后续逻辑使用。
        Optional<SysUser> existing = sysUserRepository.findByUsername(username);
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (existing.isPresent()) {
            // 行注释：声明变量 user，保存本行计算或查询得到的结果，供后续逻辑使用。
            SysUser user = existing.get();
            // 行注释：声明变量 changed，保存本行计算或查询得到的结果，供后续逻辑使用。
            boolean changed = false;

            // 行注释：声明变量 normalizedRole，保存本行计算或查询得到的结果，供后续逻辑使用。
            String normalizedRole = normalizeRole(role);
            // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
            if (!normalizedRole.equalsIgnoreCase(user.getRole())) {
                // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
                user.setRole(normalizedRole);
                // 行注释：给变量 changed 赋值，更新当前业务流程中的临时状态。
                changed = true;
            // 行注释：结束当前代码块，表示这段逻辑处理完成。
            }
            // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
            if (!"active".equalsIgnoreCase(user.getStatus())) {
                // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
                user.setStatus("active");
                // 行注释：给变量 changed 赋值，更新当前业务流程中的临时状态。
                changed = true;
            // 行注释：结束当前代码块，表示这段逻辑处理完成。
            }
            // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
            if (displayName != null && !displayName.equals(user.getDisplayName())) {
                // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
                user.setDisplayName(displayName);
                // 行注释：给变量 changed 赋值，更新当前业务流程中的临时状态。
                changed = true;
            // 行注释：结束当前代码块，表示这段逻辑处理完成。
            }
            // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
            if (rawPassword != null && !rawPassword.isBlank()
                    // 行注释：读取对象字段值，参与业务判断、计算或返回。
                    && !passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
                // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
                user.setPasswordHash(passwordEncoder.encode(rawPassword));
                // 行注释：调用 JPA 删除数据或清理过期记录。
                userSessionRepository.deleteByUser(user);
                // 行注释：给变量 changed 赋值，更新当前业务流程中的临时状态。
                changed = true;
            // 行注释：结束当前代码块，表示这段逻辑处理完成。
            }
            // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
            if (changed) {
                // 行注释：调用 JPA 保存实体，把数据写入数据库。
                return sysUserRepository.save(user);
            // 行注释：结束当前代码块，表示这段逻辑处理完成。
            }
            // 行注释：返回处理结果，最终会交给前端或上层方法使用。
            return user;
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        SysUser user = new SysUser();
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        user.setUsername(username);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        user.setDisplayName(displayName);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        user.setRole(normalizeRole(role));
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        user.setStatus("active");
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        // 行注释：调用 JPA 保存实体，把数据写入数据库。
        return sysUserRepository.save(user);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： requireUser
     * - 作用：加载必需实体，未找到时抛出明确异常。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 requireUser 方法，完成 AuthService 中对应的一步业务处理。
    // 方法用法：业务处理前调用它确认目标记录存在，不存在时抛出明确错误。
    private SysUser requireUser(Long id) {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return sysUserRepository.findById(id)
                // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： normalizeRole
     * - 作用：将输入值规范化并校验为系统使用的标准格式。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 normalizeRole 方法，完成 AuthService 中对应的一步业务处理。
    // 方法用法：写入数据前调用 normalizeRole，用于把前端输入统一成后端认可的标准值。
    private String normalizeRole(String role) {
        // 行注释：声明变量 value，保存本行计算或查询得到的结果，供后续逻辑使用。
        String value = role == null ? "viewer" : role.trim().toLowerCase();
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (!ROLES.contains(value)) {
            // 行注释：抛出明确异常，让前端收到规范的错误提示。
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role must be admin/manager/viewer");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return value;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： normalizeStatus
     * - 作用：将输入值规范化并校验为系统使用的标准格式。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 normalizeStatus 方法，完成 AuthService 中对应的一步业务处理。
    // 方法用法：写入数据前调用 normalizeStatus，用于把前端输入统一成后端认可的标准值。
    private String normalizeStatus(String status) {
        // 行注释：声明变量 value，保存本行计算或查询得到的结果，供后续逻辑使用。
        String value = status.trim().toLowerCase();
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (!USER_STATUSES.contains(value)) {
            // 行注释：抛出明确异常，让前端收到规范的错误提示。
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status must be active/disabled");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return value;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： toUserMap
     * - 作用：将实体对象转换为接口响应 Map，并保持字段命名稳定。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 toUserMap 方法，完成 AuthService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 toUserMap，用于完成 Auth 模块的一段核心业务逻辑。
    private Map<String, Object> toUserMap(SysUser user) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> map = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        map.put("id", user.getId());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        map.put("username", user.getUsername());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        map.put("display_name", user.getDisplayName());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        map.put("role", user.getRole());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        map.put("status", user.getStatus());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        map.put("created_at", user.getCreatedAt());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        map.put("last_login_at", user.getLastLoginAt());
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return map;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}


