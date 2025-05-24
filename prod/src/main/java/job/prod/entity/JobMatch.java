/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package job.prod.entity;

import edu.stanford.nlp.ling.CoreAnnotations;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.web.multipart.MultipartFile;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.CoreLabel;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class JobMatch {

    
    List<String> jobDescriptions = Arrays.asList(
    "We are looking for a Full Stack Developer to work with our existing development team on creating and maintaining the internal “buddy” platform that powers our customer boarding, in-life management, reporting and analytics as well as acting as the foundation for our future payments innovation.\n" +
"\n" +
"The ideal candidate will have a strong willingness to learn and desire to build complex business-critical applications with agile development teams. Strong JavaScript/TypeScript experience, and experience creating cloud based full stack web applications from inception to deployment would be a bonus. You should be passionate about building cutting-edge applications and eager to take control of the entire development lifecycle, playing an active role in one of Ireland’s fastest-growing FinTech companies.\n" +
"\n" +
"You will work closely with our software developers, test engineers and business analysts as part of our development team to build a secure and scalable platform.\n" +
"\n" +
"Job Description\n" +
"\n" +
"· Participate in the full development lifecycle encompassing design, development and implementation of complex full stack web applications in JavaScript/TypeScript with React and NodeJS frameworks.\n" +
"\n" +
"· Create beautiful, intuitive, and user-friendly interfaces that provide an outstanding user experience.\n" +
"\n" +
"· Architect scalable, reliable, and high-performant cloud based software systems to meet business needs.\n" +
"\n" +
"· Write clean, readable, and maintainable code, setting a high standard for future developers.\n" +
"\n" +
"· Work closely with cross-functional agile teams to define technical requirements, establish best practices, and drive innovation.\n" +
"\n" +
"· Conduct thorough code reviews to ensure code quality and adherence to industry standards and best practices.\n" +
"\n" +
"· Investigate and resolve complex technical issues that arise during the software development lifecycle.\n" +
"\n" +
"· Stay current with emerging technologies, industry trends, and best practices to ensure our tech stack remains cutting-edge.\n" +
"\n" +
"Technical Knowledge and Skills\n" +
"\n" +
"· Experience in full stack software development and a willingness to learn our tech stack.\n" +
"\n" +
"· Experience with front-end technologies such as HTML5, CSS3, and modern JavaScript frameworks.\n" +
"\n" +
"· Experience in SQL and NoSQL databases.\n" +
"\n" +
"· Familiarity with cloud services (e.g., AWS, Azure) and containerisation technologies (e.g., Docker, Kubernetes).\n" +
"\n" +
"· Experience with git version control, CI/CD pipelines and DevOps practices.\n" +
"\n" +
"· Strong problem-solving skills and the ability to debug complex issues.\n" +
"\n" +
"· Excellent communication and teamwork skills.\n" +
"\n" +
"· Ability to work in a fast-paced, agile environment.\n" +
"\n" +
"Key Competencies and Behaviours\n" +
"\n" +
"· Motivation – Self-motivated to go beyond the standard day to day workload to deliver the best result for the business and for you personally – New Payment Innovation is not looking for someone to “just get by”.\n" +
"\n" +
"· Problem Solving and Analysis – Works on problems of diverse scope where analysis is required. Demonstrates good judgement in selecting methods and techniques in obtaining solutions.\n" +
"\n" +
"· Teamwork and Collaboration – High sense of ownership and urgency to get the job done and deliver upon the objectives and goals of the business.\n" +
"\n" +
"· Accountability – Takes ownership of issues to get resolutions that benefit the business.",
    "ROLE RESPONSIBILITIES:\n" +
"\n" +
"Work with the development team on backend API's, data pipelines, and data bases.\n" +
"Manipulate and restructure data from one configuration to another to support various business and client needs.\n" +
"Respond to internal queries and data requests.\n" +
"Collaborate cross-functionally with technical and commercial teams to support product and customer development.\n" +
"ROLE REQUIREMENTS:\n" +
"\n" +
"Minimum 2 years’ experience working as a Software Developer or Support Role.\n" +
"Bachelor's degree in Computer Science or related field.\n" +
"AWS fundamentals knowledge\n" +
"Competency with Python\n" +
"Ability to liaise between technical and non-technical teams.\n" +
"Fluent English with excellent written and verbal communication skills.\n" +
"Comfortable working with cutting-edge technologies and modern data environments.\n" +
"Ability to work independently and collaboratively in a hybrid team structure.","DataVisor is a next generation security company that utilizes industry leading unsupervised machine learning to detect fraudulent activity for financial transactions, mobile user acquisition, social networks, commerce and money laundering. Our solution is used by some of the largest internet properties in the world, including Pinterest, FedEx, AirAsia, Synchrony Financial, Zomato and Ping An, to protect them from the ever-increasing risk of fraud. Our award-winning software is powered by a team of world-class experts in big data, security, and scalable infrastructure. Our culture is open, positive, collaborative, and results driven. Come join us!\n" +
"\n" +
"Summary\n" +
"\n" +
"As a platform engineer intern, you will learn how to build a next-generation machine learning platform, which incorporates our secret sauce, UML (unsupervised machine learning) with other SML (supervised machine learning) algorithms. Our team works to improve our core detection algorithms and automate the full training process. As complex fraud attacks become more prevalent, it is more important than ever to detect fraudsters in real-time. The platform team is responsible for developing the architecture that makes real-time UML possible. We are looking for creative and eager-to-learn engineers to help us expand our novel streaming and database systems, which enable our detection capabilities.\n" +
"\n" +
"This position is ideal for those who are majoring in Computer Science or Computer engineering who would like to gain some hands-on experience in fraud detection and machine learning before graduation. This 3 to 6 months internship could lead to a full-time position after graduation.\n" +
"\n" +
"\n" +
"What you'll do:\n" +
"\n" +
"Design and build machine learning systems that process data sets from the world’s largest consumer services\n" +
"Use unsupervised machine learning, supervised machine learning, and deep learning to detect fraudulent behavior and catch fraudsters\n" +
"Build and optimize systems, tools, and validation strategies to support new features\n" +
"Help design/build distributed real-time systems and features\n" +
"Use big data technologies (e.g. Spark, Hadoop, HBase, Cassandra) to build large scale machine learning pipelines\n" +
"Develop new systems on top of realtime streaming technologies (e.g. Kafka, Flink)\n" +
"Requirements\n" +
"\n" +
"BS/MS students majoring in Computer Science, Engineering or a related subject, Current students enrolled in a post-secondary program (BS or MS) who are majoring in computer science, information management, or a related field of Ireland based college or university. Ideally in his/her last school year\n" +
"Canadian citizen, permanent resident of Ireland\n" +
"Proven working experience in Java, Shell, Python development\n" +
"Excellent knowledge of Relational Databases, SQL and ORM technologies (JPA2, Hibernate) is a plus\n" +
"Experience in Cassandra, HBase, Flink, Spark or Kafka is a plus.\n" +
"Experience in the Spring Framework is a plus\n" +
"Experience with test-driven development is a plus\n" +
"Strong communication and interpersonal skills.","Role Summary:\n" +
"\n" +
"We are looking for a passionate and driven Junior Software Developer to join our team. This role is perfect for someone at the start of their career who is eager to learn, grow, and contribute to building innovative software solutions. The ideal candidate will have a strong academic background, some experience (academic or practical) with front-end frameworks and .NET Core, and an interest in working with non-relational databases. Strong communication skills, teamwork, and a proactive attitude are essential.\n" +
"\n" +
"Minimum Requirements:\n" +
"\n" +
"A minimum of a 2:1 honours degree in Computer Science, Software Engineering, Maths or a related discipline.\n" +
"Some exposure to front-end frameworks (e.g., Angular, React, Vue.js) through coursework, internships, or personal projects.\n" +
"Basic knowledge of .NET Core and its application in back-end development.\n" +
"Familiarity with non-relational databases (e.g., MongoDB) is a plus.\n" +
"Strong problem-solving skills and a willingness to learn.\n" +
"Excellent verbal and written communication skills.\n" +
"Ability to work effectively in a team environment and adapt to a collaborative work culture.\n" +
"A self-motivated attitude with a strong sense of accountability for your work.\n" +
"Desirable Skills and Experience.\n" +
"\n" +
"Exposure to cloud platforms, preferably Azure.\n" +
"Basic Understanding of Agile/Scrum methodologies.\n" +
"Familiarity with RESTful API design.\n" +
"Experience or interest in Asset management."," Graduated less than 24 months ago or about to complete a Bachelor’s or Master’s Degree in Computer Science, Computer Engineering, or related fields at time of application - Although no specific programming language is required – you should be familiar with the syntax of languages such as Java, C/C++, or Python - Knowledge of Computer Science fundamentals such as object-oriented design, algorithm design, data structures, problem solving and complexity analysis.\n" +
"Do you want to solve business challenges through innovative technology? Do you enjoy working on cutting-edge, scalable services technology in a team environment? Do you like working on industry-defining projects that move the needle? At Amazon, we hire the best minds in technology to innovate and build on behalf of our customers. The intense focus we have on our customers is why we are one of the world’s most beloved brands – customer obsession is part of our company DNA. Our Software Development Engineers (SDEs) use cutting-edge technology to solve complex problems and get to see the impact of their work first-hand. If this is you, come chart your own path at Amazon! The challenges SDEs solve for at Amazon are big and impact millions of customers, sellers, and products around the world. We’re looking for individuals who are excited by the idea of creating new products, features, and services from scratch while managing ambiguity and the pace of a company whose ship cycles are measured in weeks, not years. Key job responsibilities - Collaborate with experienced cross-disciplinary Amazonians to conceive, design, and bring to market innovative products and services. - Design and build innovative technologies in a large distributed computing environment and help lead fundamental changes in the industry. - Create solutions to run predictions on distributed systems with exposure to innovative technologies at incredible scale and speed. - Build distributed storage, index, and query systems that are scalable, fault-tolerant, low cost, and easy to manage/use. - Work in an agile environment to deliver high quality software.\n" +
"Previous technical internship(s) if applicable\n" +
"Experience with distributed, multi-tiered systems, algorithms, and relational databases\n" +
"Experience in optimization mathematics such as linear programming and nonlinear optimisation\n" +
"Ability to effectively articulate technical challenges and solutions\n" +
"Adept at handling ambiguous or undefined problems as well as ability to think abstractly.","Our world is transforming, and PTC is leading the way. Our software brings the physical and digital worlds together, enabling companies to improve operations, create better products, and empower people in all aspects of their business.\n" +
"\n" +
"Our people make all the difference in our success. Today, we are a global team of nearly 7,000 and our main objective is to create opportunities for our team members to explore, learn, and grow – all while seeing their ideas come to life and celebrating the differences that make us who we are and the work we do possible.\n" +
"\n" +
"PTC is building a new SaaS platform and we’re looking for an experienced Software Engineer to join our team to work on backend core services. As part of the core components team, you will have the opportunity to work with cutting-edge cloud technologies and build highly scalable, resilient, and secure SaaS services on which future PTC SaaS products will be delivered. This is a new platform and as such, you will have the chance to design, architect and choose technologies and patterns and build services from the ground up.\n" +
"\n" +
"You will be joining a growing team of software and cloud engineers building core components for PTC’s new SaaS platform. The team is based in Dublin and as part of the PTC SaaS Organization, you will be working with teams from around the world including USA, Israel, India and UK.\n" +
"\n" +
"Day-To-Day\n" +
"\n" +
"Develop microservice capabilities to meet business requirements\n" +
"Architect future solutions and choose technologies\n" +
"Work in an Agile environment\n" +
"Build core microservices on Kubernetes using modern languages\n" +
"Collaborate with the team to take advantage of new concepts/technologies\n" +
"Contribute to our technical direction and standards with engineers on the team\n" +
"Preferred Skills and Knowledge\n" +
"\n" +
"Good understanding of microservices and modern design patterns\n" +
"Comfortable developing with cloud technologies\n" +
"Experience with modern programming languages (Spring Boot / NodeJS / Python)\n" +
"Experience designing and building scalable and resilient SaaS services\n" +
"A passion for writing clean, testable, and scalable code\n" +
"Preferred Experience:\n" +
"\n" +
"Experience designing & implementing RESTful APIs\n" +
"Experience building microservices or SOA\n" +
"Experience building and running highly scalable services in production environments\n" +
"Basic Qualifications:\n" +
"\n" +
"Bachelor’s degree in Computer Science, Computer Engineering or equivalent\n" +
"2+ years of experience in programming and software engineering","Currently enrolled in a Bachelor’s or Master’s Degree in Computer Science, Computer Engineering, or related fields at time of application - Although no specific programming language is required – you should be familiar with the syntax of languages such as Java, C/C++, or Python - Knowledge of Computer Science fundamentals such as object-oriented design, algorithm design, data structures, problem solving and complexity analysis\n" +
"Do you want to solve business challenges through innovative technology? Do you enjoy working on cutting-edge, scalable services technology in a team environment? Do you like working on industry-defining projects that move the needle? At Amazon, we hire the best minds in technology to innovate and build on behalf of our customers. The intense focus we have on our customers is why we are one of the world’s most beloved brands – customer obsession is part of our company DNA. Our interns write real software and collaborate with a selected group of experienced software development engineers who help interns on projects that matter to our customers. We want to you to feel welcomed, included and valued right from the start. Every day will be filled with exciting new challenges, developing new skills, and achieving personal growth. How often can you say that your work changes the world? At Amazon, you’ll say it often. Join us and define tomorrow. We have two different internship durations available: - 3 months internship - 6 months internship Please ensure to indicate your availability during the application process. Key job responsibilities - Collaborate with experienced cross-disciplinary Amazonians to conceive, design, and bring to market innovative products and services. - Design and build innovative technologies in a large distributed computing environment and help lead fundamental changes in the industry. - Create solutions to run predictions on distributed systems with exposure to innovative technologies at incredible scale and speed. - Build distributed storage, index, and query systems that are scalable, fault-tolerant, low cost, and easy to manage/use. - Work in an agile environment to deliver high quality software. A day in the life As an intern, you will be matched to a manager and a mentor. You will have the opportunity to influence the evolution of Amazon technology and lead mission critical projects early in your career. Your design, code, and raw smarts will contribute to solving some of the most complex technical challenges in the areas of distributed systems, data mining, automation, optimisation, scalability, and security – just to name a few. In addition to working on an impactful project, you will have the opportunity to engage with Amazonians for both personal and professional development, expand your network, and participate in activities with other interns throughout your internship. No matter the location of your internship, we give you the tools to own your project and learn in a real-world setting. Many of our technologies overlap, and you would be hard pressed to find a team that is not using Amazon Web Services (AWS), touching the catalogue, or iterating services to better personalise for customers. We make the impossible, possible.\n" +
"Previous technical internship(s) if applicable\n" +
"Experience with distributed, multi-tiered systems, algorithms, and relational databases\n" +
"Experience in optimization mathematics such as linear programming and nonlinear optimisation\n" +
"Ability to effectively articulate technical challenges and solutions\n" +
"Adept at handling ambiguous or undefined problems as well as ability to think abstractly","What You'll Do\n" +
"Build and improve components of our agent infrastructure\n" +
"\n" +
"Work on real features that ship to production\n" +
"\n" +
"Collaborate with experienced engineers and researchers\n" +
"\n" +
"Learn about large-scale AI systems and infrastructure\n" +
"\n" +
"Contribute to our agent development platform\n" +
"\n" +
"Gain experience with cutting-edge AI tools and frameworks\n" +
"\n" +
"Technical Areas\n" +
"Backend development with Python and modern frameworks\n" +
"\n" +
"Agent system implementation and testing\n" +
"\n" +
"API development and integration\n" +
"\n" +
"Performance optimization and monitoring\n" +
"\n" +
"Infrastructure and tooling development\n" +
"\n" +
"Testing and deployment automation\n" +
"\n" +
"Requirements\n" +
"Currently pursuing BS/MS in Computer Science or related field\n" +
"\n" +
"Strong programming skills, particularly in Python\n" +
"\n" +
"Basic understanding of AI/ML concepts\n" +
"\n" +
"Experience with software development practices\n" +
"\n" +
"Ability to work independently and learn quickly\n" +
"\n" +
"Strong problem-solving abilities\n" +
"\n" +
"Nice to Have\n" +
"Experience with AI frameworks or tools\n" +
"\n" +
"Familiarity with distributed systems\n" +
"\n" +
"Open source contributions\n" +
"\n" +
"Experience with developer tools\n" +
"\n" +
"Interest in AI infrastructure","join our expert annotation team to create training data for the world's most advanced AI models. No previous AI experience is necessary. You'll get your foot in the door with one of the most prominent players in the AI/LLM space today. We're primarily seeking Python developers with 3+ years of experience to train large AI language models, helping cutting-edge generative AI models write better frontend code. Projects typically include discrete, highly variable problems that involve engaging with these models as they learn to code. We currently have 200+ roles open!\n" +
"\n" +
"What Will I Be Doing?\n" +
"\n" +
"Evaluating the quality of AI-generated code, including human-readable summaries of your rationale.\n" +
"\n" +
"Building and evaluating React components, hooks, and modern Python solutions.\n" +
"\n" +
"Solving coding problems and writing functional and efficient Python code.\n" +
"\n" +
"Writing robust test cases to confirm code works efficiently and effectively.\n" +
"\n" +
"Creating instructions to help others and reviewing code before it goes into the model.\n" +
"\n" +
"Engaging in a variety of projects, from evaluating code snippets to developing full mobile applications using chatbots.","Here's what awaits you in this exhilarating role:\n" +
"\n" +
"\n" +
"Design and Development: Design, develop, and maintain components of Grouper’s web and integrations applications, including the Grouper Portal, Grouper Hub, Data Privacy Engine, API gateway, Analytics Engines, and Data Science portal.\n" +
"Software Lifecycle Participation: Participate in the full software lifecycle, from design and development to testing, bug fixing, and cloud deployment.\n" +
"API Development: Build highly available, scalable, and client-facing APIs, employing REST and HATEOAS principles.\n" +
"Secure API Interaction: Develop and interact with secure APIs like OpenID Connect and JWT for enhanced security.\n" +
"Agile Development: Engage in an Agile Scrum-based software development process, ensuring iterative and collaborative progress.\n" +
"Asynchronous Service Communication: Demonstrate expertise in asynchronous service communication, utilizing tools such as RabbitMQ or Kafka for efficient data flow.\n" +
"\n" +
"What We're Looking For: Frontend Guru! Ready to dive into the vibrant world of web development with a team that values creativity and innovation? Here's what we're searching for:\n" +
"\n" +
"\n" +
"Experience Level: At least 3 years of experience (6+ for Senior positions) building web applications, particularly for data analytics, data visualization, and enterprise dashboards.\n" +
"Full Stack Development: Expertise with full stack web development using Java and the Spring framework.\n" +
"API Design: Proficiency in RESTful API design and integration to ensure efficient communication between components.\n" +
"Web Application Architecture: In-depth knowledge of web application architectures and design patterns to develop scalable and maintainable solutions.\n" +
"Education: University degree in Computer Science or equivalent field.\n" +
"Programming Skills: Excellent knowledge and skill in object-oriented programming, data structures, and algorithms, preferably in Python or Java.\n" +
"Cloud Computing: Familiarity with at least one major cloud computing provider such as AWS, Azure, etc., for deployment and scalability.\n" +
"Version Control: Experience with Git or other version control software to manage codebase efficiently.\n" +
"Code Quality: Takes pride in writing elegant code, optimizing runtime performance, and adhering to good programming practices.\n" +
"Testing: Experienced in developing unit tests and integration tests to ensure the reliability and robustness of the codebase.\n" +
"Collaboration: Ability to perform pair programming and peer review with fellow teammates to improve code quality and foster knowledge sharing.\n" +
"Agile Environment: Experience working in an Agile environment following the Scrum methodology for iterative development and continuous improvement.\n" +
"Creativity: Demonstrates creativity and a passion for tackling challenging data problems, with a willingness to contribute to a dynamic start-up environment.","Responsibilities:\n" +
"Develop high-quality code to meet specifications and requirements.\n" +
"Collaborate with senior engineers on architecture and design discussions.\n" +
"Participate in code reviews to learn and contribute to code quality.\n" +
"Follow development best practices, including version control and testing.\n" +
"Continuously build familiarity with large, long-lived codebases.\n" +
"\n" +
"Requirements:\n" +
"Experience developing software with Python.\n" +
"Solid understanding of Object-Oriented Programming (OOP).\n" +
"Proficiency using version control systems (e.g., Git or SVN).\n" +
"Basic familiarity with CI tools (e.g., Jenkins or similar).\n" +
"Comfortable working in a Linux development environment.\n" +
"Exposure to Test Driven Development (TDD) concepts and tools.\n" +
"\n" +
"Technical Skill Expectations:\n" +
"Ability to translate ideas into clear, maintainable, and well-tested code.\n" +
"Code is mostly bug-free in common paths and demonstrates attention to quality.\n" +
"Can navigate and understand codebases, including modules they didn't author.\n" +
"Developing the ability to complete coding tasks with increasing speed and quality.\n" +
"\n" +
"Personal Attributes:\n" +
"Wants to be part of a team that is committed to delivering high quality innovative products.\n" +
"Wants to be part of a culture that is committed to sharing and enhancing the knowledge of the wider team supporting team wide learning and success\n" +
"Have a curious mindset - about what you do, the technology you use and how you connect with others to deliver products that we are proud to deliver to our clients.","Mastercard Payment Gateway Service (MPGS) is on the lookout for a passionate and innovative Front-End Software Engineer with strong experience in React & Angular. As part of our agile and dynamic team, you will play a critical role in building and maintaining cutting-edge customer-facing solutions that are both scalable and secure. We are seeking someone who is enthusiastic about creating exceptional user experiences and driving the future of digital payments.While your primary focus will be on front-end development, you should also have a basic understanding of back-end technologies, enabling effective collaboration with back-end developers and integration of APIs and services.\n" +
"\n" +
"Role\n" +
"Design and develop dynamic, user-friendly, and responsive user interfaces using Angular and React.\n" +
"Collaborate with UX/UI designers to ensure that web applications are visually appealing, intuitive, and easy to use.\n" +
"Implement reusable and maintainable components and front-end architecture.\n" +
"Work closely with back-end developers to integrate front-end components with back-end services and APIs.\n" +
"Apply basic knowledge of Java/J2EE, Spring, JPA, and RDBMS to better understand the back-end processes and contribute to troubleshooting and debugging when necessary.\n" +
"Participate in agile ceremonies (e.g., sprint planning, daily stand-ups, retrospectives) to ensure smooth project delivery.\n" +
"Collaborate with cross-functional teams including product management, QA, and other stakeholders to ensure alignment with business goals\n" +
"\n" +
"Qualifications\n" +
"Bachelor’s degree in Computer Science, Engineering, or related field (or equivalent experience).\n" +
"2-5 years of experience in front-end development, with a basic understanding of back-end technologies\n" +
"\n" +
"Knowledge/Experience (preferred):\n" +
"Strong expertise in React & Angular, with a deep understanding of their ecosystems and Spring-boot experience (Backend)\n" +
"Proven experience building complex web applications from scratch.\n" +
"Experience with TypeScript and modern frontend build tools (e.g., Webpack, Babel).\n" +
"Familiarity with RESTful APIs and asynchronous request handling.\n" +
"Experience with continuous integration/continuous deployment (CI/CD) pipelines.\n" +
"Strong understanding of version control tools (e.g., Git).\n" +
"Strong understanding of agile methodologies and tools (e.g., Jira, Confluence).\n" +
"Basic familiarity with Java programming.\n" +
"\n" +
"Skills and Abilities:\n" +
"Strong analytical and problem-solving skills.\n" +
"Excellent communication and collaboration skills, with a focus on teamwork.\n" +
"High attention to detail and commitment to delivering high-quality user experiences.\n" +
"A proactive attitude and eagerness to learn and adapt in a fast-paced environment.","Full job description\n" +
"Frontend developers at Kooba work in small, collaborative teams to create web experiences for some of Ireland & Europe’s biggest brands. They work closely with UX & UI designers, backend developers and project managers to deliver projects which reach the highest quality standards.\n" +
"\n" +
"Kooba’s approach emphasises hands-on skills across all departments. We are looking for someone with outstanding knowledge about the web and a passion for ensuring best practices are followed and emerging technologies are used to their best potential.\n" +
"\n" +
"The role:\n" +
"Transform UI designs to frontend prototypes that are fully responsive across all devices and adhere to browser compatibility best practices.\n" +
"Ensure the final quality of your project meet the highest quality standards\n" +
"Key skills and experience:\n" +
"Hands-on web development experience, ideally in an agency environment\n" +
"Strong understanding of core JavaScript proficiency\n" +
"Experience with one or more Javascript frameworks, such as Astro JS, React, Svelte or Vue\n" +
"Solid grasp of HTML and CSS, leveraging CSS preprocessors like Sass or CSS frameworks like Tailwind\n" +
"Demonstrated understanding of task runners such as Vite or Webpack\n" +
"Experience using GIT as part of a development team\n" +
"An understanding of automated deployment processes\n" +
"Strong organisational skills and ability to manage your time effectively\n" +
"Understanding of the requirements to deliver AA conformant websites\n" +
"A keen eye for detail\n" +
"Ability to communicate directly with clients\n" +
"A robust approach to testing and QA\n" +
"Nice to have:\n" +
"3D and animation skills (Three JS, Web GL, GSAP etc.)\n" +
"Backend development experience\n" +
"QQI Level 6 education or higher\n" +
"In return for your great work, we promise you:\n" +
"A beautiful working space - both in Dublin & Berlin\n" +
"The very best equipment\n" +
"Competitive salary\n" +
"A hybrid working arrangement\n" +
"Last but not least - an award-winning team of nice people, hungry for new ideas, collaborations and processes!","ou will join a dynamic, multi-functional team that not only designs but also supports their services in production! This role will enable you to gain comprehensive experience in all aspects of the Software Development lifecycle, from initial design through to successful implementation and ongoing support. You will collaborate closely with product management and UX design teams, ensuring flawless delivery of services that meet our high standards.\n" +
"The day-to-day:\n" +
"Develop and maintain web applications using modern frontend and backend technologies.\n" +
"Engage with multi-functional teams in designing, building, and shipping new features.\n" +
"Write clean, maintainable, and efficient code.\n" +
"Provide on-call production support for Advertising products.\n" +
"Engage in code reviews and actively contribute to the team's development.\n" +
"Stay up to date with the latest industry trends and technologies.\n" +
"Maintain API services and front-end libraries that serve Ads in passenger experiences.\n" +
"What you'll need:\n" +
"Bachelor's degree or higher in computer science, Engineering, or a related field or equivalent work experience.\n" +
"Experience with version control systems like Git.\n" +
"Experience with frontend technologies like HTML, CSS, JavaScript, and frameworks like React or Angular.\n" +
"Experience with backend technologies such as Node.js, Python, Ruby, or Java.\n" +
"Understanding of RESTful APIs and web services.\n" +
"Strong problem-solving skills and attention to detail.\n" +
"Excellent communication and collaboration skills.\n" +
"What will help you on the job:\n" +
"Familiarity with Design Patterns and how to apply them.\n" +
"Knowledge of cloud platforms such as AWS, Azure, or Google Cloud.\n" +
"Familiarity with CI/CD pipelines and DevOps practices.\n" +
"Experience with testing frameworks and tools.\n" +
"Familiarity with database technologies.","Join our expert annotation team to create training data for the world's most advanced AI models. No previous AI experience is necessary. You'll get your foot in the door with one of the most prominent players in the AI/LLM space today. We're primarily seeking JavaScript/React developers with 3+ years of experience to train large AI language models, helping cutting-edge generative AI models write better frontend code. Projects typically include discrete, highly variable problems that involve engaging with these models as they learn to code. We currently have 200+ roles open!\n" +
"\n" +
"What Will I Be Doing?\n" +
"\n" +
"Evaluating the quality of AI-generated code, including human-readable summaries of your rationale.\n" +
"\n" +
"Building and evaluating React components, hooks, and modern JavaScript solutions.\n" +
"\n" +
"Solving coding problems and writing functional and efficient JavaScript/React code.\n" +
"\n" +
"Writing robust test cases to confirm code works efficiently and effectively.\n" +
"\n" +
"Creating instructions to help others and reviewing code before it goes into the model.\n" +
"\n" +
"Engaging in a variety of projects, from evaluating code snippets to developing full mobile applications using chatbots.\n" +
"\n" +
"Pay Rates\n" +
"\n" +
"Compensation rates average at $30/hr and can go up to $50+/hr. Expectations are 15+ hours per week; however, there is no upper limit. You can work as much as you want and will be paid weekly per hour of work done on the platform.\n" +
"\n" +
"Contract Length\n" +
"\n" +
"This is a long-term contract with no end date. We expect to have work for the next 2 years. You can end the contract at any time, but we hope you will commit to 12 months of work.\n" +
"\n" +
"Flexible Schedules\n" +
"\n" +
"Developers can set their own hours. Ideal candidates will be interested in spending 40 hours a week. You will be assigned to projects, so strong performers will adapt to the urgency of projects and stay engaged, but we are incredibly flexible on working hours. You can take a 3-hour lunch with no problem. Instead of tracking your hours, you are paid according to time spent on the platform, calculated in the coding exercises."
            ,"Here's what awaits you in this exhilarating role:\n" +
"\n" +
"\n" +
"Expertise in Angular Ecosystem: Desirable experience and proficiency in developing within the Angular ecosystem, enabling seamless integration and optimization of frontend components.\n" +
"Architectural Vision: Architect efficient and reusable frontend components for the portal platform, emphasizing maintainability and reusability to streamline development processes.\n" +
"User-Centric Solutions: Collaborate closely with stakeholders and product managers to design and implement user-centric frontend solutions, ensuring alignment with user needs and business objectives.\n" +
"Cross-Platform Compatibility: Craft solutions for diverse device layouts and styling issues across multiple browsers and platforms, ensuring a consistent and seamless user experience.\n" +
"Clean Code Mastery: Create clean, organized, and modular HTML and CSS code, leveraging contemporary techniques, tools, and libraries to enhance code quality and maintainability.\n" +
"UX Design Integration: Ensure the technical feasibility of UX design concepts, aligning frontend development with relevant design flows to deliver intuitive and visually appealing user interfaces.\n" +
"Innovative Prototyping: Build proof of concepts to drive development and business decisions, leveraging prototypes to explore and validate innovative solutions.\n" +
"Industry Best Practices: Understand and promote industry best practices and modern design trends, staying abreast of emerging technologies and methodologies to drive continuous improvement.\n" +
"Agile Collaboration: Participate actively in an Agile Scrum based software development process, contributing to all stages of the software lifecycle, from design and development to testing, bug fixing, and cloud deployment.\n" +
"\n" +
"What We're Looking For: Frontend Guru! Ready to take the lead in crafting cutting-edge web applications and dive into complex data problems and craft elegant solutions? Here's what we're searching for:\n" +
"\n" +
"\n" +
"Frontend Development Experience: Demonstrated expertise in developing cross-platform web applications, with a preference for experience with Angular.\n" +
"Commercial Software Background: Minimum of 2 years of commercial software experience, showcasing your ability to thrive in professional software development environments.\n" +
"JavaScript Proficiency: Strong proficiency in building web applications with JavaScript and popular frameworks like Angular, React, Bootstrap, JQuery, etc. Experience with automated testing frameworks such as PhantomJS, Jasmine, etc. is highly valued.\n" +
"Web Application Architecture: In-depth knowledge of web application architectures and design patterns, enabling you to design robust and scalable frontend solutions.\n" +
"Cloud Computing Familiarity: Familiarity with at least one large cloud computing provider like AWS, Azure, etc., ensuring your ability to deploy and scale web applications in cloud environments.\n" +
"Containerization Skills: Experience with containerizing applications using Docker, facilitating efficient deployment and management of web application environments.\n" +
"Version Control Proficiency: Experience with Git or other version control software, demonstrating your ability to effectively collaborate on codebases and manage changes across projects.\n" +
"\n" +
"The Perfect Match: If you possess some of these skills and are ready to make a significant impact in the realm of data analytics, Grouper is excited to hear from you!\n" +
"\n" +
"\n" +
"Educational Background: Bachelor’s Degree or higher in Computer Science/Engineering, providing a solid foundation for software development.\n" +
"Commitment to Quality: Demonstrated commitment to writing elegant code and prioritizing software functionality, with a focus on optimizing runtime performance and adhering to good programming practices.\n" +
"Testing Expertise: Experienced in developing unit tests and integration tests, with a dedication to ensuring code quality. Proficiency with testing frameworks like Jasmine and E2E testing frameworks is preferred.\n" +
"Collaborative Approach: Ability to thrive in a collaborative environment, including performing pair programming and participating in peer reviews to uphold code standards and foster team cohesion.\n" +
"Passion for Innovation: Creativity and a passion for tackling challenging data problems are essential, coupled with a willingness to embrace the dynamic environment of a startup.\n" +
"Communication Skills: Strong communication and interpersonal skills are required to effectively collaborate with team members and stakeholders, ensuring alignment and successful project outcomes.","About the role: We are seeking a talented frontend developer to join our team as a founding member, focusing on rapidly developing and deploying dynamic user interfaces for products built on our custom multi-agent system stack. This role involves creating intuitive, responsive, and powerful interfaces that enable users to interact seamlessly with our AI systems. The ideal candidate will have expertise in modern frontend development, UI/UX design principles, and experience with real-time web applications and AI-powered interfaces.\n" +
"\n" +
"Design and implement intuitive, responsive interfaces that effectively visualize complex AI agent interactions and workflows\n" +
"\n" +
"Develop and optimize React/JavaScript applications that support AI-driven user experiences\n" +
"\n" +
"Rapidly prototype and iterate on UIs based on user feedback and evolving product requirements\n" +
"\n" +
"Work closely with our AI engineering team to ensure frontend components effectively leverage our multi-agent system capabilities\n" +
"\n" +
"Create reusable UI components and establish frontend architecture patterns for future development\n" +
"\n" +
"Implement real-time data visualization and interaction patterns that help users understand and control AI agent behaviors\n" +
"\n" +
"Ensure high performance, accessibility, and cross-browser compatibility of all user interfaces\n" +
"\n" +
"Contribute to the overall product vision and help define the user experience of our multi-agent systems\n" +
"\n" +
"In this role, you're a good fit if you have:\n" +
"\n" +
"Bachelor's degree in Computer Science, Software Engineering, Web Development, Design, or related field\n" +
"\n" +
"3+ years of experience in frontend development with a strong portfolio demonstrating expertise in React, TypeScript, JavaScript, Vue, or similar modern frontend frameworks\n" +
"\n" +
"Experience with state management libraries (Redux, MobX, Zustand), responsive web design, and building complex interactive user interfaces\n" +
"\n" +
"Strong understanding of UI/UX design principles, HTML5, CSS3, and best practices for AI-driven applications\n" +
"\n" +
"Familiarity with WebSocket, GraphQL, REST APIs or similar technologies for real-time data handling\n" +
"\n" +
"Experience with data visualization libraries (D3.js, Chart.js, Three.js, etc.)\n" +
"\n" +
"Knowledge of frontend build tools and workflows (Webpack, Vite, npm, yarn)\n" +
"\n" +
"Strong problem-solving skills and ability to work in a fast-paced startup environment\n" +
"\n" +
"Excellent communication skills and ability to collaborate with cross-functional teams\n" +
"\n" +
"Passion for AI technologies, LLMs, and human-AI interaction paradigms","Key Responsibilities:\n" +
"\n" +
"Develop, test and maintain backend services and APIs using Node.js and TypeScript.\n" +
"Collaborate with frontend developers and blockchain engineers to ensure seamless integration with smart contracts and web applications.\n" +
"Work with databases (SQL and GraphQl) to efficiently store and manage data.\n" +
"Assist in debugging and resolving backend issues.\n" +
"Stay up-to-date with the latest trends in DeFi, blockchain, and backend technologies.\n" +
"Requirements:\n" +
"\n" +
"Experience: Minimum of 1 year as a Backend Developer or similar role (can include internships or personal projects).\n" +
"TypeScript: Familiar, ideally with experience in Node.js.\n" +
"Web3: Basic knowledge of blockchains, DeFi protocols and smart contracts.\n" +
"APIs: Experience working with either RESTful or GraphQL APIs.\n" +
"DevOps: Basic understanding of modern DevOps practices and tools e.g. Docker, CI/CD pipelines.\n" +
"Version Control: Familiar with Git.\n" +
"Education: Bachelor's or Master's degree in Computer Science, Engineering, or a related field (or equivalent experience).\n" +
"Soft Skills: Strong problem-solving capabilities, excellent communication, a collaborative spirit, and the ability to work in an agile environment.","Experience (non-internship) in professional front end, web or mobile software development using JavaScript, HTML and CSS - Experience in computer science fundamentals (object-oriented design, data structures, algorithm design, problem solving and complexity analysis) - Experience using JavaScript frameworks such as angular and react\n" +
"Are you a passionate, self-starting frontend engineer? Interested in building amazing mobile and web experiences? We are looking for a frontend engineer to join our team. You will be working on A to Z, Amazon’s flagship employee mobile and web eco-system, used daily by millions of Amazonians. You'll work at the forefront of technology, leveraging the latest AWS services and industry-leading practices to deliver a dynamic, personalized, and fresh mobile and web experience to all of our users. We use the latest mobile native, web and LLM/GenAI technologies that can scale to serve millions of Amazonians globally. This role offers continuous opportunities to innovate as you develop new features, enhance existing tools, and design seamless integrations across Amazon's technical landscape. Key job responsibilities * Design and deliver mobile applications that serve our entire global workforce. Amazon employees are your customers. * Build fresh and dynamic front-ends that are pixel-perfect using React and other technologies for both mobile and web * Assist in building end-to-end solutions using modern serverless technologies and AWS services, ensuring exceptional performance and reliability * Drive data-driven technical decisions while maintaining high standards in complex, ambiguous environments * Collaborate with product and design partners to create intuitive user experiences that simplify complex workflows * Innovate and improve our engineering practices while maintaining operational excellence * Mentor fellow engineers and contribute to building a strong engineering culture * Take ownership of critical features from design through production, ensuring they meet enterprise-scale requirements Join a team that's passionate about technology, committed to excellence, and believes in having fun while building impactful solutions that matter. A day in the life As part of our team, you'll collaborate with stakeholders to create seamless user experiences while adhering to platform standards and governance. You'll solve complex technical challenges that impact millions of users. Your work will directly influence how Amazonians interact with essential workplace tools and information. About the team Within Amazonian Experience and Technology (AET), the A to Z Experiences team builds and maintains Amazon's primary employee platform across mobile and web. We own critical touchpoints including homepages, search functionality, and global navigation, serving a diverse workforce of 1.2M associates and 400K corporate employees. Our platform processes 3.9B annual interactions, making us essential to Amazonians' daily work lives - from managing schedules and pay to accessing benefits and career resources.\n" +
"Knowledge of professional software engineering & best practices for full software development life cycle, including coding standards, software architectures, code reviews, source control management, continuous deployments, testing, and operational excellence","Job Role / Responsibilities\n" +
"\n" +
"\n" +
"This position is responsible for performing all forms of complex technical security assessments of clients’ information technology systems (including the Internet, Intranet, Applications, Hosts, Firewalls, Mobile applications etc.) and related policies and procedures. Communication in both written and verbal forms, including training courses, workshops, consultations with clients and formal reports. These assessments are conducted on a by-project basis, ranging between one and three weeks per project, and will occasionally be done at the client site. This role is also required to conduct ongoing research in the IT security arena and regularly assist in the sales process.\n" +
"\n" +
"\n" +
"Responsibilities include:\n" +
"\n" +
"Perform security reviews of architecture and application designs, as well as application source code reviews\n" +
"Perform mobile, application, infrastructure, as well as social engineering assessments and penetration testing\n" +
"Exploit vulnerabilities to gain access, and expand access to remote systems\n" +
"Document technical issues identified during security assessments\n" +
"Research cutting-edge security topics and new attack vectors\n" +
"Communication of findings/innovations internally, to colleagues\n" +
"Represent Integrity360 at international events, forums or training events\n" +
"Required Skills / Experience\n" +
"\n" +
"3+ years experience in information security\n" +
"Industry experience working in Dev/Admin/Engineer field\n" +
"Industry experience working in the Information security field as a penetration tester\n" +
"Development and/or source code review experience in C/C++, C#, VB.NET, ASP, PHP, Java, Python or Ruby\n" +
"Unix, Windows and networking security skills\n" +
"Familiarity with general application and network security concepts\n" +
"Excellent communication skills\n" +
"Manual penetration testing experience above and beyond running automated tools\n" +
"Networking knowledge (MPLS, BGP, OSPF, IPv6, TOR)\n" +
"Understanding of Security Auditing tools\n" +
"Ethical hacking experience\n" +
"Report writing experience\n" +
"Presentation skills\n" +
"Scripting/coding, sys admin or networking experience\n" +
"Understanding of programming methodologies in one or more programming or scripting languages: Java, Python, C/C++, C#,.NET, ASP, PHP, etc.\n" +
"Personal Attributes\n" +
"\n" +
"Urgent desire to learn with a passion for technology\n" +
"Motivated by being at the forefront of technology, and pushing technology boundaries\n" +
"Motivated by the thrill of being able to “break” systems\n" +
"Willingness to work late when required\n" +
"Willingness to travel when required, possibly abroad\n" +
"Analytical and critical thinker\n" +
"Good written English skills\n" +
"Problem-solving skills","Relevant Experience\n" +
"5 - 7 yrs\n" +
"\n" +
"Educational Qualification\n" +
"BE / B Tech /ME /M Tech/ MCA /M. Sc\n" +
"\n" +
"Job Description\n" +
"Design and delivery of Salesforce.com based solutions\n" +
"Collaborate with Client business representatives to capture business requirements\n" +
"Prototype new solutions\n" +
"Support detailed Quality assurance testing of solutions\n" +
"Integrate with existing client technology solutions and platforms\n" +
"Prepare and deployment CRM Solutions to Test and Production environments\n" +
"\n" +
"Required Skills\n" +
"5 years+ experience delivering Enterprise solutions in Java or MS .Net\n" +
"2 years+ experience developing Salesforce.com based solutions\n" +
"Strong working knowledge of Sales Cloud, Service Cloud, and Force.com platform\n" +
"Strong analysis and design Skills\n" +
"In-depth Object modelling Skills\n" +
"Database development experience using MS SQL Server or Oracle\n" +
"\n" +
"Preferred Skills\n" +
"Certified Force.com Developer (Dev 401)\n" +
"VisualForce and Apex Development experience\n" +
"Web Services design and delivery experience\n" +
"Agile/ Scrum delivery experience\n" +
"Experience developing Web applications using HTML, CSS3, Javascript and JQuery Mobile\n" +
"Experience with Automated Unit Testing e.g. JUnit\n" +
"\n" +
"Desired & Personal Attributes\n" +
"Good written and oral communication\n" +
"Ability to think creatively and solve complex problems\n" +
"Team player with strong interpersonal skills and a willingness to help others\n" +
"Ability to thrive in a cross-functional team on business critical projects","Are you a software engineer who enjoys working in a fast-paced environment? Are you naturally curious and a lifelong learner? Do you live and breathe technology, have strong analytical skills and enjoy problem solving? If you enjoy working with and leveraging cutting–edge AI tooling (think Copilot and Cursor) then this role is for you. Or if you want to contribute to building innovative AI-powered products and intelligent agents using the latest in GenAI, including platforms like AWS, Bedrock and Hugging Face and models like Claude and Llama the please apply.\n" +
"\n" +
"Career development is incredibly important to us at Fenergo as our success is based on the growth of our people. This is why we actively seek the best and the brightest to join us. Further your career with an opportunity that connects you to a strong network, enables you to do your best work and actively supports your career.\n" +
"\n" +
"You Should Be\n" +
"\n" +
"A full stack software engineer who thinks about complex systems, the components that make them and opportunities to simplify them\n" +
"Excited about imagining ways to solve business problems using technology\n" +
"Passionate about using cloud computing and cloud-based services to deliver software solutions to customers\n" +
"Hold yourself and others to high-quality standards and help people meet that level\n" +
"Take pride in working on projects to successful completion involving a wide variety of technologies and systems\n" +
"Thrive in a collaborative environment where engineering, product, sales and leadership come together to produce world class software\n" +
"Requirements\n" +
"\n" +
"Key Expectations\n" +
"\n" +
"Has experience working with Serverless architectures and frameworks\n" +
"Experience of cloud development (AWS or Azure)\n" +
"Successful delivery of projects to production environments\n" +
"2+ years' experience developing multi-layer systems using C#.NET, JAVA OR C++\n" +
"1+ years' experience using React.\n" +
"Continuous Delivery and Continuous Integration experience\n" +
"Experience working on Agile methodology\n" +
"Degree or Diploma in Computing or similar related qualification\n" +
"Strong communication skills.\n" +
"Our promise to you\n" +
"\n" +
"We are striving to become global leaders across all of the categories we operate in and as part of that we are a high-performing highly collaborative team that works cross functionally to accommodate our clients needs.\n" +
"\n" +
"What we value is at the CORE of how we succeed:\n" +
"\n" +
"Collaboration: Working together to achieve our best\n" +
"Outcomes: Drive Success in every engagement\n" +
"Respect: A collective feeling of inclusion and belonging\n" +
"Excellence: Continuously raising the bar","PTC is building a new SaaS platform and we’re looking for an experienced Software Engineer to join our team to work on backend core services. As part of the core components team, you will have the opportunity to work with cutting-edge cloud technologies and build highly scalable, resilient, and secure SaaS services on which future PTC SaaS products will be delivered. This is a new platform and as such, you will have the chance to design, architect and choose technologies and patterns and build services from the ground up.\n" +
"\n" +
"You will be joining a growing team of software and cloud engineers building core components for PTC’s new SaaS platform. The team is based in Dublin and as part of the PTC SaaS Organization, you will be working with teams from around the world including USA, Israel, India and UK.\n" +
"\n" +
"Day-To-Day\n" +
"\n" +
"Develop microservice capabilities to meet business requirements\n" +
"Architect future solutions and choose technologies\n" +
"Work in an Agile environment\n" +
"Build core microservices on Kubernetes using modern languages\n" +
"Collaborate with the team to take advantage of new concepts/technologies\n" +
"Contribute to our technical direction and standards with engineers on the team\n" +
"Preferred Skills and Knowledge\n" +
"\n" +
"Good understanding of microservices and modern design patterns\n" +
"Comfortable developing with cloud technologies\n" +
"Experience with modern programming languages (Spring Boot / NodeJS / Python)\n" +
"Experience designing and building scalable and resilient SaaS services\n" +
"A passion for writing clean, testable, and scalable code\n" +
"Preferred Experience:\n" +
"\n" +
"Experience designing & implementing RESTful APIs\n" +
"Experience building microservices or SOA\n" +
"Experience building and running highly scalable services in production environments\n" +
"Basic Qualifications:\n" +
"\n" +
"Bachelor’s degree in Computer Science, Computer Engineering or equivalent\n" +
"2+ years of experience in programming and software engineering","Mastercard Payment Gateway Service (MPGS) is on the lookout for a passionate and innovative Front-End Software Engineer with strong experience in React & Angular. As part of our agile and dynamic team, you will play a critical role in building and maintaining cutting-edge customer-facing solutions that are both scalable and secure. We are seeking someone who is enthusiastic about creating exceptional user experiences and driving the future of digital payments.While your primary focus will be on front-end development, you should also have a basic understanding of back-end technologies, enabling effective collaboration with back-end developers and integration of APIs and services.\n" +
"\n" +
"Role\n" +
"Design and develop dynamic, user-friendly, and responsive user interfaces using Angular and React.\n" +
"Collaborate with UX/UI designers to ensure that web applications are visually appealing, intuitive, and easy to use.\n" +
"Implement reusable and maintainable components and front-end architecture.\n" +
"Work closely with back-end developers to integrate front-end components with back-end services and APIs.\n" +
"Apply basic knowledge of Java/J2EE, Spring, JPA, and RDBMS to better understand the back-end processes and contribute to troubleshooting and debugging when necessary.\n" +
"Participate in agile ceremonies (e.g., sprint planning, daily stand-ups, retrospectives) to ensure smooth project delivery.\n" +
"Collaborate with cross-functional teams including product management, QA, and other stakeholders to ensure alignment with business goals\n" +
"\n" +
"Qualifications\n" +
"Bachelor’s degree in Computer Science, Engineering, or related field (or equivalent experience).\n" +
"2-5 years of experience in front-end development, with a basic understanding of back-end technologies\n" +
"\n" +
"Knowledge/Experience (preferred):\n" +
"Strong expertise in React & Angular, with a deep understanding of their ecosystems and Spring-boot experience (Backend)\n" +
"Proven experience building complex web applications from scratch.\n" +
"Experience with TypeScript and modern frontend build tools (e.g., Webpack, Babel).\n" +
"Familiarity with RESTful APIs and asynchronous request handling.\n" +
"Experience with continuous integration/continuous deployment (CI/CD) pipelines.\n" +
"Strong understanding of version control tools (e.g., Git).\n" +
"Strong understanding of agile methodologies and tools (e.g., Jira, Confluence).\n" +
"Basic familiarity with Java programming.\n" +
"\n" +
"Skills and Abilities:\n" +
"Strong analytical and problem-solving skills.\n" +
"Excellent communication and collaboration skills, with a focus on teamwork.\n" +
"High attention to detail and commitment to delivering high-quality user experiences.\n" +
"A proactive attitude and eagerness to learn and adapt in a fast-paced environment.","We are seeking a highly motivated Software Engineer (Backend) Intern to join our Autodesk Platform Services Data Models teams. As a member of the team, you will play an important role in the definition and development of core technologies for building the next generation of cloud and data platform. The ideal candidate will be a hands-on craftsperson who loves to solve challenging practical problems related to cloud platforms, data streaming, backend development, APIs, and Cloud/Web applications.\n" +
"Responsibilities\n" +
"Experiment, learn, and grow using latest cutting edge cloud and data streaming technologies\n" +
"Develop backend APIs for cloud and data platform\n" +
"Develop data streaming apps, create observability metrics, work on CI/CD pipelines\n" +
"Implement prototypes and software components\n" +
"Collaborate with our team of engineers to design, plan, develop, test, deliver and maintain complex features and new subsystems\n" +
"Document and present your ideas and solutions accurately and thoroughly\n" +
"Keep up to date with latest trends and technologies to anticipate future development needs and requirements\n" +
"Minimum Qualifications\n" +
"BS or MS Student in Computer Science or a related technical field\n" +
"Detail oriented and passionate about building software and technology\n" +
"A constant desire to improve, learn more and take things to the next level\n" +
"The Ideal Candidate\n" +
"Experience with Git or equivalent revision control systems\n" +
"Familiarity with software development and design principles\n" +
"Java programming skills\n" +
"In addition, AWS, Python, CI/CD or DevOps is plus\n" +
"Excellent written and oral communication skills in English\n" +
"Knows about data streaming applications, designs and challenges","Join our expert annotation team to create training data for the world's most advanced AI models. No previous AI experience is necessary. You'll get your foot in the door with one of the most prominent players in the AI/LLM space today. We're primarily seeking JavaScript/React developers with 3+ years of experience to train large AI language models, helping cutting-edge generative AI models write better frontend code. Projects typically include discrete, highly variable problems that involve engaging with these models as they learn to code. We currently have 200+ roles open!\n" +
"\n" +
"What Will I Be Doing?\n" +
"\n" +
"Evaluating the quality of AI-generated code, including human-readable summaries of your rationale.\n" +
"\n" +
"Building and evaluating React components, hooks, and modern JavaScript solutions.\n" +
"\n" +
"Solving coding problems and writing functional and efficient JavaScript/React code.\n" +
"\n" +
"Writing robust test cases to confirm code works efficiently and effectively.\n" +
"\n" +
"Creating instructions to help others and reviewing code before it goes into the model.\n" +
"\n" +
"Engaging in a variety of projects, from evaluating code snippets to developing full mobile applications using chatbots.\n" +
"\n" +
"Pay Rates\n" +
"\n" +
"Compensation rates average at $30/hr and can go up to $50+/hr. Expectations are 15+ hours per week; however, there is no upper limit. You can work as much as you want and will be paid weekly per hour of work done on the platform.\n" +
"\n" +
"Contract Length\n" +
"\n" +
"This is a long-term contract with no end date. We expect to have work for the next 2 years. You can end the contract at any time, but we hope you will commit to 12 months of work.\n" +
"\n" +
"Flexible Schedules\n" +
"\n" +
"Developers can set their own hours. Ideal candidates will be interested in spending 40 hours a week. You will be assigned to projects, so strong performers will adapt to the urgency of projects and stay engaged, but we are incredibly flexible on working hours. You can take a 3-hour lunch with no problem. Instead of tracking your hours, you are paid according to time spent on the platform, calculated in the coding exercises.\n" +
"\n" +
"Interview Process\n" +
"\n" +
"Apply using this Ashby form.\n" +
"\n" +
"If you seem like a good fit, we'll send an async RLHF code review that will take 35 minutes and must be finished within 72 hours of us sending it.\n" +
"\n" +
"You’ll receive credentials to the RLHF platform. We’re doing regular calls to answer any further questions about onboarding, as well as providing a support team at your disposal.\n" +
"\n" +
"You’ll perform a simulated production-level task (RLHF task) on the platform. This will be the final stage, which will ultimately determine your leveling and which project you’ll be assigned. Successful completion of this process provides you with an opportunity to work on projects as they become available.\n" +
"\n" +
"Tech Stack Priorities\n" +
"\n" +
"The current priority for this team is frontend engineers who are well versed in JavaScript, React, and modern web development frameworks and libraries.\n" +
"\n" +
"Required Qualifications\n" +
"\n" +
"3+ years of experience in a software engineering/software development role.\n" +
"\n" +
"Strong proficiency with JavaScript/React and frontend development.\n" +
"\n" +
"Complete fluency in the English language.\n" +
"\n" +
"Ability to articulate complex technical concepts clearly and engagingly.\n" +
"\n" +
"Excellent attention to detail and ability to maintain consistency in writing. Solid understanding of grammar, punctuation, and style guidelines.\n" +
"\n" +
"Nice To Haves:\n" +
"\n" +
"Bachelor's or Master's degree in Computer Science.\n" +
"\n" +
"Experience with modern JavaScript frameworks and libraries (Next.js, Vue, Angular).\n" +
"\n" +
"Familiarity with frontend testing frameworks (Jest, React Testing Library, Cypress).\n" +
"\n" +
"Knowledge of state management solutions (Redux, Context API, MobX).\n" +
"\n" +
"Experience with TypeScript and modern frontend tooling.\n" +
"\n" +
"Recognized accomplishments or contributions to the coding community or in projects.\n" +
"\n" +
"Proven analytical skills with an ability to approach problems creatively.\n" +
"\n" +
"Adept communication skills, especially when understanding and discussing project requirements.\n" +
"\n" +
"A commitment to continuous learning and staying updated with the latest coding advancements and best practices.\n" +
"\n" +
"Enthusiasm for teaching AI models and experience with technical writing!","What’s on offer at D&B Ireland\n" +
"25 days annual leave (plus 2 paid volunteer days & 1 paid un-sick day)\n" +
"Holiday buy & sell (the option to buy or sell up to 5 additional days per year)\n" +
"Flexible working - hybrid model\n" +
"Employee Health Insurance\n" +
"Mental Health Support program\n" +
"Pension Contribution\n" +
"Family Friendly Leave (Maternity, Paternity, Parental, Marriage and Bereavement)\n" +
"Life Assurance\n" +
"Educational Assistance Program\n" +
"Life-Style Account (D&B will match your contributions up to €40 per month and can be used to claim for a range of health-related, leisure or lifestyle activities)\n" +
"\n" +
"At Dun & Bradstreet, we are 6,000 friendly colleagues around the world waiting to meet you and give you the opportunity to grow your career.\n" +
"What does this mean?\n" +
"Development of cutting-edge technologies including:\n" +
"Big Data\n" +
"API technologies\n" +
"Cloud Infrastructure\n" +
"Participation in and contribution to all stages of the development lifecycle\n" +
"Working in cross-functional agile teams, you will be developing features end-to-end across the software stack whilst ensuring non-functional requirements are met\n" +
"You will produce elegant, yet simple code that lends itself to low cost maintenance, extensibility, testability and performance\n" +
"You will explore new opportunities using proof-of-concepts, wire frames and early experience prototypes\n" +
"Your test-driven development ethos will expose you to continual interactions with the QA team to analyse, resolve, and verify any defects\n" +
"How will this work?\n" +
"Join an agile development team and work alongside experienced engineers to help develop and build world-class solutions\n" +
"In-role training on using the tools, techniques and best practices\n" +
"We won’t just let you sink or swim! You will be allocated a buddy and go through an orientation program. This will familiarise you with D&B culture, who’s who and how to get stuff done in D&B\n" +
"You will be given the platform to contribute your ideas at every step of your journey\n" +
"About you!\n" +
"BSc in Computer Science or related discipline or relevant experience\n" +
"Relevant work experience is advantageous.\n" +
"Qualities we are interested in;\n" +
"Demonstrable Java and OR Python coding standards\n" +
"Understanding of Design Patterns\n" +
"Exposure of applying test driven development techniques using frameworks\n" +
"A passion and ability to write well documented, maintainable, and testable code\n" +
"A genuine hunger to build a career in technology and software development with a thorough knowledge of web trends, best practices, and new technologies with the drive to stay informed on upcoming trends\n" +
"\n" +
"Our 12 month graduate program starts the beginning of September 2025\n" +
"\n" +
"All employees and contractors working in D&B should be aware that they have responsibilities in relation to the Company’s Business Management System. This relates to information and its security, quality, environment and health and safety both during and post-employment with D&B\n" +
"\n" +
"Dun & Bradstreet is an Equal Opportunity Employer\n" +
"\n" +
"All Dun & Bradstreet job postings can be found at https://www.dnb.com/about-us/careers-and-people/joblistings.html and https://jobs.lever.co/dnb. Official communication from Dun & Bradstreet will come from an email address ending in @dnb.com.\n" +
"\n" +
"Notice to Applicants: Please be advised that this job posting page is hosted and powered by Lever. Your use of this page is subject to Lever's Privacy Notice and Cookie Policy, which governs the processing of visitor data on this platform.","What you'll be doing:\n" +
"Architect, design and develop RESTful API Endpoints, ensuring high performance, scalability and maintainability.\n" +
"Collaborate with other Front-End Engineers and develop dynamic, responsive and user-friendly web applications.\n" +
"Define Data Models and database schemas and manage relational databases or NoSQL databases ensuring data integrity and performance.\n" +
"Create and maintain documentation of application design, configuration and maintenance.\n" +
"Provide accurate timelines for specific tasks assigned.\n" +
"Participate in scheduled on-call rotation and respond to emergencies.\n" +
"What we're looking for:\n" +
"3+ years professional experience working with medium/large complex code bases\n" +
"Experience in Full Stack development\n" +
"Experience with one or more of the following programming languages: Java, Go, or Python\n" +
"Experience in REST API design and implementation\n" +
"Proficient in HTML, CSS, the JavaScript ecosystem and familiarity with popular frameworks like React, Angular, Ember, Jest, Babel, and TypeScript\n" +
"Knowledgeable in UI/UX design principles\n" +
"Strong communication and interpersonal skills\n" +
"Desire to collaborate and to develop strong and positive team relationships\n" +
"Attention to detail, organizational skills, a strong work ethic and the ability to work independently.\n" +
"Preferred Qualifications:\n" +
"Bachelor's degree in Computer Science or related field, or at least three years of proven experience in lieu of a degree\n" +
"Demonstrated ability to write clean, maintainable, and well-documented code.\n" +
"Database management, cloud technologies (AWS), and containerization (Docker, Kubernetes)\n" +
"Knowledge of React best practices\n" +
"Familiarity with DevOps tools and practices, including CI/CD pipelines\n" +
"Automated deployment tools (e.g. Terraform, Ansible, Jenkins)\n" +
"Excellent problem-solving skills and attention to detail\n" +
"Experience with Agile development methodologies such as SAFe, Scrum\n" +
"Equal Opportunity Statement:\n" +
"\n" +
"Sony is an Equal Opportunity Employer. All persons will receive consideration for employment without regard to gender (including gender identity, gender expression and gender reassignment), race (including colour, nationality, ethnic or national origin), religion or belief, marital or civil partnership status, disability, age, sexual orientation, pregnancy, maternity or parental status, trade union membership or membership in any other legally protected category.\n" +
"\n" +
"We strive to create an inclusive environment, empower employees and embrace diversity. We encourage everyone to respond.\n" +
"\n" +
"PlayStation is a Fair Chance employer and qualified applicants with arrest and conviction records will be considered for employment.","Working on cloud based streaming telemetry services and applications; The Cloud Vision Portal is a web-based front-end application that abstracts the physical network to a broader, network-wide perspective. This consists of a front-end user interface for understanding the stored data from the real-time telemetry streamed from the network switches. It allows you to view events, inspect a device, or compare metrics across devices and much more is planned as it is a key part of Arista's Any Cloud Platform and also the Cognitive Campus initiative.\n" +
"\n" +
"What You'll Do\n" +
"\n" +
"You will be part of a team building the user interface based on a streaming telemetry service for networking devices that send real-time updates to a cloud-based data ingestion infrastructure. You will actively participate in designing and implementing a top-quality data exploration and device management user interface, including visualization and correlation of events, network anomaly detection and configuration of networking devices using NodeJS, React and Redux as the pillars of the architecture with ES6 scripting and various tools including Webpack, Storybook,TypeScript and Less.\n" +
"\n" +
"One of the many attractions of joining Arista is that from the top Arista is an engineering company. The founders and engineering managers are all engineers who understand good software engineering and the importance of doing things right.Arista hires directly into the broader team, regardless of geography.\n" +
"\n" +
"Our engineers have full ownership of their projects. Our management structure is flat and lightweight. Software engineering is run by software engineers who are in charge of delivering features from concept through to completion. We put a premium on building and using test automation tools.No part of the company is off-limits, meaning that our engineers have the chance to work on a variety of different areas.\n" +
"\n" +
"\n" +
"#LI-EO1\n" +
"\n" +
"\n" +
"Qualifications\n" +
"5+ Strong development experience with JavaScript, including experience with frameworks like Angular or React\n" +
"Familiarity with build and continuous integration using webpack and npm\n" +
"Ability to convert wireframes and mock-ups into high quality components\n" +
"Commitment to producing high quality, performant and reliable software\n" +
"An open mind to change with willingness and desire to improve\n" +
"Enjoyment of writing code and solving problems\n" +
"Ability to appreciate a culture of invention, quality, respect and fun\n" +
"Professional experience an advantage","Join our expert annotation team to create training data for the world's most advanced AI models. No previous AI experience is necessary. You'll get your foot in the door with one of the most prominent players in the AI/LLM space today. We're primarily seeking JavaScript/React developers with 3+ years of experience to train large AI language models, helping cutting-edge generative AI models write better frontend code. Projects typically include discrete, highly variable problems that involve engaging with these models as they learn to code. We currently have 200+ roles open!\n" +
"\n" +
"What Will I Be Doing?\n" +
"\n" +
"Evaluating the quality of AI-generated code, including human-readable summaries of your rationale.\n" +
"\n" +
"Building and evaluating React components, hooks, and modern JavaScript solutions.\n" +
"\n" +
"Solving coding problems and writing functional and efficient JavaScript/React code.\n" +
"\n" +
"Writing robust test cases to confirm code works efficiently and effectively.\n" +
"\n" +
"Creating instructions to help others and reviewing code before it goes into the model.\n" +
"\n" +
"Engaging in a variety of projects, from evaluating code snippets to developing full mobile applications using chatbots.\n" +
"\n" +
"Pay Rates\n" +
"\n" +
"Compensation rates average at $30/hr and can go up to $50+/hr. Expectations are 15+ hours per week; however, there is no upper limit. You can work as much as you want and will be paid weekly per hour of work done on the platform.\n" +
"\n" +
"Contract Length\n" +
"\n" +
"This is a long-term contract with no end date. We expect to have work for the next 2 years. You can end the contract at any time, but we hope you will commit to 12 months of work.\n" +
"\n" +
"Flexible Schedules\n" +
"\n" +
"Developers can set their own hours. Ideal candidates will be interested in spending 40 hours a week. You will be assigned to projects, so strong performers will adapt to the urgency of projects and stay engaged, but we are incredibly flexible on working hours. You can take a 3-hour lunch with no problem. Instead of tracking your hours, you are paid according to time spent on the platform, calculated in the coding exercises.\n" +
"\n" +
"Interview Process\n" +
"\n" +
"Apply using this Ashby form.\n" +
"\n" +
"If you seem like a good fit, we'll send an async RLHF code review that will take 35 minutes and must be finished within 72 hours of us sending it.\n" +
"\n" +
"You’ll receive credentials to the RLHF platform. We’re doing regular calls to answer any further questions about onboarding, as well as providing a support team at your disposal.\n" +
"\n" +
"You’ll perform a simulated production-level task (RLHF task) on the platform. This will be the final stage, which will ultimately determine your leveling and which project you’ll be assigned. Successful completion of this process provides you with an opportunity to work on projects as they become available.\n" +
"\n" +
"Tech Stack Priorities\n" +
"\n" +
"The current priority for this team is frontend engineers who are well versed in JavaScript, React, and modern web development frameworks and libraries.\n" +
"\n" +
"Required Qualifications\n" +
"\n" +
"3+ years of experience in a software engineering/software development role.\n" +
"\n" +
"Strong proficiency with JavaScript/React and frontend development.\n" +
"\n" +
"Complete fluency in the English language.\n" +
"\n" +
"Ability to articulate complex technical concepts clearly and engagingly.\n" +
"\n" +
"Excellent attention to detail and ability to maintain consistency in writing. Solid understanding of grammar, punctuation, and style guidelines.\n" +
"\n" +
"Nice To Haves:\n" +
"\n" +
"Bachelor's or Master's degree in Computer Science.\n" +
"\n" +
"Experience with modern JavaScript frameworks and libraries (Next.js, Vue, Angular).\n" +
"\n" +
"Familiarity with frontend testing frameworks (Jest, React Testing Library, Cypress).\n" +
"\n" +
"Knowledge of state management solutions (Redux, Context API, MobX).\n" +
"\n" +
"Experience with TypeScript and modern frontend tooling.\n" +
"\n" +
"Recognized accomplishments or contributions to the coding community or in projects.\n" +
"\n" +
"Proven analytical skills with an ability to approach problems creatively.\n" +
"\n" +
"Adept communication skills, especially when understanding and discussing project requirements.\n" +
"\n" +
"A commitment to continuous learning and staying updated with the latest coding advancements and best practices.\n" +
"\n" +
"Enthusiasm for teaching AI models and experience with technical writing!\n" +
"\n" +
"If you're passionate about JavaScript, React, and the future of frontend development, this is an excellent opportunity to contribute to cutting-edge AI technology while leveraging your expertise!","The Software Implementation Consultant supports the end-to-end customer journey for the software side of the projects, working closely with sales, end users/integrators, project management and engineering. This role ensures that clients have a smooth transition from contract signing through to project delivery, providing operational and communication support in a fast-paced, tech-focused environment.\n" +
"\n" +
"What we offer\n" +
"\n" +
"Moffett Automation is a company of approx. 100 staff that is expanding steadily due to market demands. We are an industry leader in what we do which is developing software and producing automated pallet shuttles and other products to move pallets around a warehouse.\n" +
"\n" +
"We have developed our software fully in-house, and it consists of three main components: Warehouse Control System Software, Machine Software (Shuttles, Lifts and Conveyors) and Frontend (and Backend). We use .NET Core and .NET 6 for all our software and Blazor for our frontend user interfaces.\n" +
"\n" +
"Who we are looking for\n" +
"\n" +
"Responsibilities\n" +
"\n" +
"Advising sales and high potential customers on the capabilities of our software and which modules/features are suitable\n" +
"Assist in gathering and organizing technical requirements from clients\n" +
"Creating bespoke APIs for each client depending on their requirements\n" +
"Coordinate the onboarding process for new software clients, including setting up test environment systems so that the software can be tested.\n" +
"Debugging issues on test system and production system and pushing updates to resolve these issues\n" +
"Assisting on site engineers during commissioning phases and refining system to optimise operations\n" +
"Track project deliverables and timelines using Jira\n" +
"Serve as a liaison between clients and internal technical teams, ensuring accurate information flow and expectations management\n" +
"Help prepare product walkthroughs, demos, and project materials in collaboration with product and engineering teams\n" +
"Assess change requests with relevant developers/ project managers and offer solutions to customers.\n" +
"Must have skills:\n" +
"\n" +
"1–3 years in a tech or software-related company or software degree\n" +
"Comfortable working with technical teams and understanding basic software concepts (APIs, integrations, user flows, etc.)\n" +
"Strong written and verbal communication skills to work with clients and internal stakeholders\n" +
"Leading meetings with customers and resolving issues\n" +
"Detail-oriented for documentation required on customer requirements and change requests\n" +
"Ability to manage multiple projects and prioritise work as necessary\n" +
"Experience with any of the following would be an advantage:\n" +
"\n" +
"Working in a similar advisory and implementation software role\n" +
"Familiar with warehouse operations/WMS/WCS\n" +
"C#\n" +
"Ability to create endpoints on REST API\n" +
"MySQL/databases\n" +
"Benefits\n" +
"\n" +
"25 days annual leave\n" +
"Pension scheme\n" +
"Flexible working hours\n" +
"Death in benefit\n" +
"Employee Assistance Programme for you and your family\n" +
"Bereavement leave\n" +
"Free parking\n" +
"Recreational facilities on-site\n" +
"This position is ideally hybrid role to support sales but there is the opportunity for a remote role if the candidate is residing in Ireland and the skills/experience match\n" +
"\n" +
"We do not offer relocation packages and all applicants must have a valid work permit\n" +
"\n" +
"Job Type: Full-time\n" +
"\n" +
"Benefits:\n" +
"\n" +
"Company events\n" +
"Employee assistance program\n" +
"Flexitime\n" +
"On-site parking\n" +
"Work from home\n" +
"Schedule:\n" +
"\n" +
"Monday to Friday\n" +
"Education:\n" +
"\n" +
"Bachelor's (preferred)\n" +
"Experience:\n" +
"\n" +
"C#: 2 years (preferred)","Job Description\n" +
"\n" +
"Proficiency in backend web development in Typescript/Javascript (NodeJS).\n" +
"Proficiency in widely-used DBMS technologies, such as:\n" +
"MongoDB\n" +
"PostgreSQL\n" +
"Knowledge of version control (git)\n" +
"Experience in any of the following is an advantage:\n" +
"CQRS + Event sourcing\n" +
"Microservices\n" +
"REST\n" +
"GraphQL\n" +
"gRPC\n" +
"ActiveMQ\n" +
"Redis\n" +
"Docker\n" +
"Responsibilities\n" +
"\n" +
"Participate in the entire application lifecycle focusing on building the backend software\n" +
"Write clean code and tests to develop a functional and reliable APIs for web applications\n" +
"Collaborate with Frontend Engineers to integrate APIs\n" +
"Build reusable code and libraries to simplify future developments\n" +
"Troubleshoot and debug applications\n" +
"Collaborate with various departments such as product and design to gather specifications and requirements\n" +
"Collaborate and work with QA to ensure high-quality output\n" +
"Follow emerging technologies\n" +
"Adapt to reasonable changes","You'll be joining a team of engineers across Frontend, Backend, SRE and QA. We're organised into cross-functional development teams assigned to specific verticals.\n" +
"\n" +
"This role is open for several teams, and we will define the exact team that you will be joining during the interview process based on the business needs and your preferences.\n" +
"\n" +
"Regardless of the specific team, you will be working on building tools, APIs and integrations for one of our products.\n" +
"\n" +
"Our backend is built with Elixir and Phoenix, with a Postgres database. We use React and Nextjs for our front-end. Gitlab is used as a version control tool, issue tracker and a CI/CD solution. Our applications are hosted on AWS. We fully rely on our CI for deployments and deploy multiple times per day.\n" +
"\n" +
"What this job can offer you\n" +
"Complex and meaningful challenges — solving them will enable people and businesses to live and operate in any country of the world.\n" +
"Opportunity to have a significant impact on the business — we are still very early in our journey as a company, and each change you make today is amplified by the company's growth.\n" +
"A lot of freedom to organize your work and life — you are not bound to daily standups, recurring meetings or other ceremonies.\n" +
"Competitive salary, stock options, unlimited PTO and a set of perks and benefits.\n" +
"A supportive and kind work environment where we would like you to challenge the dogmas and pursue innovation!\n" +
"Strong team of experienced engineers who will support and facilitate your professional growth.\n" +
"What you bring\n" +
"Must have (professional experience):\n" +
"\n" +
"Significant experience as a Backend Engineer working with Elixir, which includes building, shipping and maintaining a complicated software project\n" +
"Postgres (or similar)\n" +
"CI/CD (GitLab, Github, Jenkins or similar)\n" +
"Nice to have\n" +
"\n" +
"Kubernetes\n" +
"Docker\n" +
"AWS\n" +
"Nextjs\n" +
"React/Vue/Angular\n" +
"Key Responsibilities\n" +
"Lead the development of major team-scoped projects, participate in cross-team initiatives\n" +
"Actively participate in product work in the team: provide feedback, suggest solutions to the problems. Use technical insights and expertise to suggest product improvements\n" +
"Maintain good understanding of the team’s domain, both from product and engineering sides\n" +
"Provide feedback on code reviews\n" +
"Contribute to the shared codebase\n" +
"Debug and solve technical and business issues\n" +
"Participate in non-team activities, such as support rotations, hiring process, RFC discussions, etc\n" +
"Mentor and provide guidance to other engineers\n" +
"Investigate, propose and participate in implementation of improvements to our platform\n" +
"Design and implement APIs with performance, scalability, and maintainability in mind\n" +
"Practicals\n" +
"Reporting line: Engineering Team Leader\n" +
"Team: Engineering\n" +
"Location: Anywhere in the World\n" +
"Start date: As soon as possible","You'll be joining a team of engineers across Frontend, Backend, SRE and QA. We're organised into cross-functional development teams assigned to specific verticals.\n" +
"\n" +
"This role is open for several teams, and we will define the exact team that you will be joining during the interview process based on the business needs and your preferences.\n" +
"\n" +
"Regardless of the specific team, you will be working on building tools, APIs and integrations for one of our products.\n" +
"\n" +
"Our backend is built with Elixir and Phoenix, with a Postgres database. We use React and Nextjs for our front-end. Gitlab is used as a version control tool, issue tracker and a CI/CD solution. Our applications are hosted on AWS. We fully rely on our CI for deployments and deploy multiple times per day.\n" +
"\n" +
"What this job can offer you\n" +
"Complex and meaningful challenges — solving them will enable people and businesses to live and operate in any country of the world.\n" +
"Opportunity to have a significant impact on the business — we are still very early in our journey as a company, and each change you make today is amplified by the company's growth.\n" +
"A lot of freedom to organize your work and life — you are not bound to daily standups, recurring meetings or other ceremonies.\n" +
"Competitive salary, stock options, unlimited PTO and a set of perks and benefits.\n" +
"A supportive and kind work environment where we would like you to challenge the dogmas and pursue innovation!\n" +
"Strong team of experienced engineers who will support and facilitate your professional growth.\n" +
"What you bring\n" +
"Must have (professional experience):\n" +
"\n" +
"Significant experience as a Backend Engineer working with Elixir, which includes building, shipping and maintaining a complicated software project\n" +
"Postgres (or similar)\n" +
"CI/CD (GitLab, Github, Jenkins or similar)\n" +
"Nice to have\n" +
"\n" +
"Kubernetes\n" +
"Docker\n" +
"AWS\n" +
"Nextjs\n" +
"React/Vue/Angular\n" +
"Key Responsibilities\n" +
"Lead the development of major team-scoped projects, participate in cross-team initiatives\n" +
"Actively participate in product work in the team: provide feedback, suggest solutions to the problems. Use technical insights and expertise to suggest product improvements\n" +
"Maintain good understanding of the team’s domain, both from product and engineering sides\n" +
"Provide feedback on code reviews\n" +
"Contribute to the shared codebase\n" +
"Debug and solve technical and business issues\n" +
"Participate in non-team activities, such as support rotations, hiring process, RFC discussions, etc\n" +
"Mentor and provide guidance to other engineers\n" +
"Investigate, propose and participate in implementation of improvements to our platform\n" +
"Design and implement APIs with performance, scalability, and maintainability in mind\n" +
"Practicals\n" +
"Reporting line: Engineering Team Leader\n" +
"Team: Engineering\n" +
"Location: Anywhere in the World\n" +
"Start date: As soon as possible\n" +
"Remote Compensation Philosophy\n" +
"Remote's Total Rewards philosophy is to ensure fair, unbiased compensation and fair equity pay along with competitive benefits in all locations in which we operate. We do not agree to or encourage cheap-labor practices and therefore we ensure to pay above in-location rates. We hope to inspire other companies to support global talent-hiring and bring local wealth to developing countries.\n" +
"\n" +
"At first glance our salary bands seem quite wide - here is some context. At Remote we have international operations and a globally distributed workforce. We use geo ranges to consider geographic pay differentials as part of our global compensation strategy to remain competitive in various markets while we hiring globally.\n" +
"\n" +
"The base salary range for this full-time position is between USD 51,850 to USD 116,650. Our salary ranges are determined by role, level and location, and our job titles may span more than one career level. The actual base pay for the successful candidate in this role is dependent upon many factors such as location, transferable or job-related skills, work experience, relevant training, business needs, and market demands. The base salary range may be subject to change.","You'll be joining a team of engineers across Frontend, Backend, SRE and QA. We're organised into cross-functional development teams assigned to specific verticals.\n" +
"\n" +
"This role is open for several teams, and we will define the exact team that you will be joining during the interview process based on the business needs and your preferences.\n" +
"\n" +
"Regardless of the specific team, you will be working on building tools, APIs and integrations for one of our products.\n" +
"\n" +
"Our backend is built with Elixir and Phoenix, with a Postgres database. We use React and Nextjs for our front-end. Gitlab is used as a version control tool, issue tracker and a CI/CD solution. Our applications are hosted on AWS. We fully rely on our CI for deployments and deploy multiple times per day.\n" +
"\n" +
"What this job can offer you\n" +
"Complex and meaningful challenges — solving them will enable people and businesses to live and operate in any country of the world.\n" +
"Opportunity to have a significant impact on the business — we are still very early in our journey as a company, and each change you make today is amplified by the company's growth.\n" +
"A lot of freedom to organize your work and life — you are not bound to daily standups, recurring meetings or other ceremonies.\n" +
"Competitive salary, stock options, unlimited PTO and a set of perks and benefits.\n" +
"A supportive and kind work environment where we would like you to challenge the dogmas and pursue innovation!\n" +
"Strong team of experienced engineers who will support and facilitate your professional growth.\n" +
"What you bring\n" +
"Must have (professional experience):\n" +
"\n" +
"Significant experience as a Backend Engineer working with Elixir, which includes building, shipping and maintaining a complicated software project\n" +
"Postgres (or similar)\n" +
"CI/CD (GitLab, Github, Jenkins or similar)\n" +
"Nice to have\n" +
"\n" +
"Kubernetes\n" +
"Docker\n" +
"AWS\n" +
"Nextjs\n" +
"React/Vue/Angular\n" +
"Key Responsibilities\n" +
"Lead the development of major team-scoped projects, participate in cross-team initiatives\n" +
"Actively participate in product work in the team: provide feedback, suggest solutions to the problems. Use technical insights and expertise to suggest product improvements\n" +
"Maintain good understanding of the team’s domain, both from product and engineering sides\n" +
"Provide feedback on code reviews\n" +
"Contribute to the shared codebase\n" +
"Debug and solve technical and business issues\n" +
"Participate in non-team activities, such as support rotations, hiring process, RFC discussions, etc\n" +
"Mentor and provide guidance to other engineers\n" +
"Investigate, propose and participate in implementation of improvements to our platform\n" +
"Design and implement APIs with performance, scalability, and maintainability in mind\n" +
"Practicals\n" +
"Reporting line: Engineering Team Leader\n" +
"Team: Engineering\n" +
"Location: Anywhere in the World\n" +
"Start date: As soon as possible\n" +
"Remote Compensation Philosophy\n" +
"Remote's Total Rewards philosophy is to ensure fair, unbiased compensation and fair equity pay along with competitive benefits in all locations in which we operate. We do not agree to or encourage cheap-labor practices and therefore we ensure to pay above in-location rates. We hope to inspire other companies to support global talent-hiring and bring local wealth to developing countries.\n" +
"\n" +
"At first glance our salary bands seem quite wide - here is some context. At Remote we have international operations and a globally distributed workforce. We use geo ranges to consider geographic pay differentials as part of our global compensation strategy to remain competitive in various markets while we hiring globally.\n" +
"\n" +
"The base salary range for this full-time position is between USD 51,850 to USD 116,650. Our salary ranges are determined by role, level and location, and our job titles may span more than one career level. The actual base pay for the successful candidate in this role is dependent upon many factors such as location, transferable or job-related skills, work experience, relevant training, business needs, and market demands. The base salary range may be subject to change.\n" +
"\n" +
"Application process\n" +
"Interview with our recruiter\n" +
"Interview with an Engineering Leader\n" +
"(async) Code exercise and review\n" +
"Interview with the members of the engineering team\n" +
"Bar Raiser Interview\n" +
"Executive Interview with VP of Engineering\n" +
"Offer and Prior employment verification check\n" +
"#LI-DNP\n" +
"\n" +
"Benefits\n" +
"Our full benefits & perks are explained in our handbook at remote.com/r/benefits. As a global company, each country works differently, but some benefits/perks are for all Remoters:\n" +
"work from anywhere\n" +
"flexible paid time off\n" +
"flexible working hours (we are async)\n" +
"16 weeks paid parental leave\n" +
"mental health support services\n" +
"stock options\n" +
"learning budget\n" +
"home office budget & IT equipment\n" +
"budget for local in-person social events or co-working spaces","At Flipdish, we're turning the tables in favour of independent restaurant and takeaway owners by providing them with the tools they need to make their lives easier. We offer a suite of powerful yet straightforward tech solutions along with real human support, helping our customers thrive on their own terms.\n" +
"\n" +
"Everything we do is designed to make running a restaurant satisfyingly simple in a world that's anything but.\n" +
"\n" +
"About this role…\n" +
"We're hiring a Software Engineer to join our Client Experience team. This team focuses on building the self-service tools that help Flipdish clients manage their business, from onboarding through to day-to-day operations. We're creating a standout platform that makes it easy for clients to launch quickly, stay in control, and get value fast.\n" +
"\n" +
"The client experience team has a particular focus on the self service and maintenance of our products, through their online portal. Our main goal is to provide our clients with a standout management platform for all their Flipdish services. We are also responsible for creating an efficient and exciting onboarding flow, allowing the clients to start earning money fast!\n" +
"\n" +
"This role is ideal for someone with 3-5 years as a front end or full stack developer using React and Typescript. You should have previous experience working in the cloud, preferably using serverless architecture. You should have a passion for technology and be willing to take on new challenges and input to the company's growth.\n" +
"Note that this is a full stack role, and you should be keen to learn and expand in to both FE and BE codebases.\n" +
"\n" +
"In this job, you'll…\n" +
"Work hands on with our Portal code (React) and other areas of our codebase, ensuring to always deliver high quality solutions\n" +
"Work with tests, both automated and manual to ensure your code is always production ready\n" +
"Own and maintain your own CI/CD pipelines\n" +
"Understand our customers and their problems, ensuring that you are always focused on the right thing\n" +
"Take part in continuous learning, furthering both your technical ability and soft-skills\n" +
"Mentor other engineers, pair/mob code and undertake code reviews\n" +
"Implement automation wherever possible\n" +
"We're looking for…\n" +
"5+ years of development experience, working with React, Typescript (mandatory) and C# (advantageous), preferably using Domain Driven Design and Event Driven Architectures\n" +
"Ability to work across the full stack, owning your own vertical slice of the product\n" +
"Experience in the cloud, either AWS or Azure, preferably using a serverless framework such as AWS Lambda\n" +
"Good knowledge of DevOps principals, ideally some previous experience writing and maintaining pipelines for Continuous Delivery\n" +
"Excellent communication and collaboration skills – you will work with a multi-cultural, global team\n" +
"A proactive problem-solver who thrives in ambiguity and can distil complexity into pragmatic, scalable solutions","It all started in sunny San Diego, California in 2004 when a visionary engineer, Fred Luddy, saw the potential to transform how we work. Fast forward to today — ServiceNow stands as a global market leader, bringing innovative AI-enhanced technology to over 8,100 customers, including 85% of the Fortune 500®. Our intelligent cloud-based platform seamlessly connects people, systems, and processes to empower organizations to find smarter, faster, and better ways to work. But this is just the beginning of our journey. Join us as we pursue our purpose to make the world work better for everyone.\n" +
"\n" +
"\n" +
"Job Description\n" +
"\n" +
"What you get to do in this role:\n" +
"\n" +
"Build high-quality, clean, scalable and reusable code by enforcing best practices around software engineering architecture and processes (Code Reviews, Unit testing, etc.)\n" +
"Work with the product owners to understand detailed requirements and own your code from design, implementation, test automation and delivery of high-quality product to our users.\n" +
"Implement software that is simple to use to allow customers to extend and customize the functionality to meet their specific needs\n" +
"Contribute to the design and implementation of new products and features while also enhancing the existing product suite\n" +
"Be a mentor for colleagues and help promote knowledge-sharing\n" +
"\n" +
"Qualifications\n" +
"\n" +
"To be successful in this role you have:\n" +
"\n" +
"Experience in leveraging or critically thinking about how to integrate AI into work processes, decision-making, or problem-solving. This may include using AI-powered tools, automating workflows, analyzing AI-driven insights, or exploring AI's potential impact on the function or industry.\n" +
"4+ years of experience with Java or a similar OO language\n" +
"Passion for JavaScript and the Web as a platform, reusability, and componentization\n" +
"Experience with data structures, algorithms, object-oriented design, design patterns, and performance/scale considerations\n" +
"Experience with any of the modern UI frameworks like Angular, React or Vue\n" +
"Analytical and design skills","Frontend developers at Kooba work in small, collaborative teams to create web experiences for some of Ireland & Europe’s biggest brands. They work closely with UX & UI designers, backend developers and project managers to deliver projects which reach the highest quality standards.\n" +
"\n" +
"Kooba’s approach emphasises hands-on skills across all departments. We are looking for someone with outstanding knowledge about the web and a passion for ensuring best practices are followed and emerging technologies are used to their best potential.\n" +
"\n" +
"The role:\n" +
"Transform UI designs to frontend prototypes that are fully responsive across all devices and adhere to browser compatibility best practices.\n" +
"Ensure the final quality of your project meet the highest quality standards\n" +
"Key skills and experience:\n" +
"Hands-on web development experience, ideally in an agency environment\n" +
"Strong understanding of core JavaScript proficiency\n" +
"Experience with one or more Javascript frameworks, such as Astro JS, React, Svelte or Vue\n" +
"Solid grasp of HTML and CSS, leveraging CSS preprocessors like Sass or CSS frameworks like Tailwind\n" +
"Demonstrated understanding of task runners such as Vite or Webpack\n" +
"Experience using GIT as part of a development team\n" +
"An understanding of automated deployment processes\n" +
"Strong organisational skills and ability to manage your time effectively\n" +
"Understanding of the requirements to deliver AA conformant websites\n" +
"A keen eye for detail\n" +
"Ability to communicate directly with clients\n" +
"A robust approach to testing and QA\n" +
"Nice to have:\n" +
"3D and animation skills (Three JS, Web GL, GSAP etc.)\n" +
"Backend development experience\n" +
"QQI Level 6 education or higher\n" +
"In return for your great work, we promise you:\n" +
"A beautiful working space - both in Dublin & Berlin\n" +
"The very best equipment\n" +
"Competitive salary\n" +
"A hybrid working arrangement\n" +
"Last but not least - an award-winning team of nice people, hungry for new ideas, collaborations and processes!","At Covalen, we're not just a business process outsourcing (BPO) service provider – we're industry pioneers collaborating with organizations worldwide for over 25 years. From established sectors to cutting-edge industries, our tailored BPO solutions forge powerful partnerships, helping clients achieve their unique goals. We've built enduring relationships in Financial Services, Technology, and Utilities, working with some of the globe's largest and most forward-thinking companies.\n" +
"\n" +
"Job Description:\n" +
"\n" +
"Our client is a global leader in information management, where innovation, creativity, and\n" +
"\n" +
"collaboration are the key components of our corporate culture. As a member of our team, you\n" +
"\n" +
"will have the opportunity to partner with the most highly regarded companies in the world,\n" +
"\n" +
"tackle complex issues, and contribute to projects that shape the future of digital transformation\n" +
"\n" +
"You will be working within and promoting the Company Values – Be Brave, Be Wise, Be Proud\n" +
"\n" +
"and Exceed.\n" +
"\n" +
"Duties and Responsibilities :\n" +
"\n" +
"Development of Cloud-based web applications\n" +
"Work within a project team to deliver projects to specification\n" +
"Development using C#, .NET Core, HTML, and JavaScript\n" +
"Design & develop code to meet QA specifications & requirements\n" +
"Required\n" +
"\n" +
"Primary degree in Software Engineering, Computer Science or related discipline\n" +
"2-4 years’ experience as a Software Engineer\n" +
"Web application development using ASP.NET and C#\n" +
"Familiarity with MVC .NET and associated technologies\n" +
"Knowledge of developing RESTful web services\n" +
"SQL Server Database programming experience\n" +
"Good OO design skills\n" +
"Desirable\n" +
"\n" +
"Experience of using JavaScript frameworks such as jQuery\n" +
"Familiarity with Agile methods\n" +
"Familiarity with Design Patterns\n" +
"Experience of automated unit and acceptance testing\n" +
"Experience of developing and consuming Web Services\n" +
"Experience of Azure cloud platform\n" +
"Experience of DevOps processes and tool\n" +
"Benefits\n" +
"\n" +
"Career Development\n" +
"\n" +
"Training & Development\n" +
"\n" +
"Employee Engagement\n" +
"\n" +
"Employee Assistance Program (EAP)\n" +
"\n" +
"Hive Medical Cash Plan\n" +
"\n" +
"PRSA and Bike-to-Work Scheme\n" +
"\n" +
"Tax Saver Tickets\n" +
"\n" +
"Be part of a great, friendly and a diverse team\n" +
"\n" +
"What would you bring? As a valued team member, you'll play a crucial role in delivering customized BPO solutions, ensuring our clients achieve consistent high performance and meet their strategic objectives. Your expertise will contribute to our legacy of success in providing outsourced solutions for Fortune 500 companies.\n" +
"\n" +
"Ready to be a part of our dynamic team? Explore exciting opportunities with Covalen – where your skills, ideas, and achievements are celebrated!\n" +
"\n" +
"Covalen, we champion diversity and equality, anchoring our workplace cultures and creative minds. We recognize the collective strength found in the diverse backgrounds, skills, and experiences of our team members. Our commitment to fostering an inclusive environment transcends gender, marital status, family status, age, disability, sexual orientation, race, religion, and membership in the Travelling community.","Here's what awaits you in this exhilarating role:\n" +
"\n" +
"\n" +
"Expertise in Angular Ecosystem: Desirable experience and proficiency in developing within the Angular ecosystem, enabling seamless integration and optimization of frontend components.\n" +
"Architectural Vision: Architect efficient and reusable frontend components for the portal platform, emphasizing maintainability and reusability to streamline development processes.\n" +
"User-Centric Solutions: Collaborate closely with stakeholders and product managers to design and implement user-centric frontend solutions, ensuring alignment with user needs and business objectives.\n" +
"Cross-Platform Compatibility: Craft solutions for diverse device layouts and styling issues across multiple browsers and platforms, ensuring a consistent and seamless user experience.\n" +
"Clean Code Mastery: Create clean, organized, and modular HTML and CSS code, leveraging contemporary techniques, tools, and libraries to enhance code quality and maintainability.\n" +
"UX Design Integration: Ensure the technical feasibility of UX design concepts, aligning frontend development with relevant design flows to deliver intuitive and visually appealing user interfaces.\n" +
"Innovative Prototyping: Build proof of concepts to drive development and business decisions, leveraging prototypes to explore and validate innovative solutions.\n" +
"Industry Best Practices: Understand and promote industry best practices and modern design trends, staying abreast of emerging technologies and methodologies to drive continuous improvement.\n" +
"Agile Collaboration: Participate actively in an Agile Scrum based software development process, contributing to all stages of the software lifecycle, from design and development to testing, bug fixing, and cloud deployment.\n" +
"\n" +
"What We're Looking For: Frontend Guru! Ready to take the lead in crafting cutting-edge web applications and dive into complex data problems and craft elegant solutions? Here's what we're searching for:\n" +
"\n" +
"\n" +
"Frontend Development Experience: Demonstrated expertise in developing cross-platform web applications, with a preference for experience with Angular.\n" +
"Commercial Software Background: Minimum of 2 years of commercial software experience, showcasing your ability to thrive in professional software development environments.\n" +
"JavaScript Proficiency: Strong proficiency in building web applications with JavaScript and popular frameworks like Angular, React, Bootstrap, JQuery, etc. Experience with automated testing frameworks such as PhantomJS, Jasmine, etc. is highly valued.\n" +
"Web Application Architecture: In-depth knowledge of web application architectures and design patterns, enabling you to design robust and scalable frontend solutions.\n" +
"Cloud Computing Familiarity: Familiarity with at least one large cloud computing provider like AWS, Azure, etc., ensuring your ability to deploy and scale web applications in cloud environments.\n" +
"Containerization Skills: Experience with containerizing applications using Docker, facilitating efficient deployment and management of web application environments.\n" +
"Version Control Proficiency: Experience with Git or other version control software, demonstrating your ability to effectively collaborate on codebases and manage changes across projects.\n" +
"\n" +
"The Perfect Match: If you possess some of these skills and are ready to make a significant impact in the realm of data analytics, Grouper is excited to hear from you!\n" +
"\n" +
"\n" +
"Educational Background: Bachelor’s Degree or higher in Computer Science/Engineering, providing a solid foundation for software development.\n" +
"Commitment to Quality: Demonstrated commitment to writing elegant code and prioritizing software functionality, with a focus on optimizing runtime performance and adhering to good programming practices.\n" +
"Testing Expertise: Experienced in developing unit tests and integration tests, with a dedication to ensuring code quality. Proficiency with testing frameworks like Jasmine and E2E testing frameworks is preferred.\n" +
"Collaborative Approach: Ability to thrive in a collaborative environment, including performing pair programming and participating in peer reviews to uphold code standards and foster team cohesion.\n" +
"Passion for Innovation: Creativity and a passion for tackling challenging data problems are essential, coupled with a willingness to embrace the dynamic environment of a startup.\n" +
"Communication Skills: Strong communication and interpersonal skills are required to effectively collaborate with team members and stakeholders, ensuring alignment and successful project outcomes.\n" +
"\n" +
"Meet Grouper:\n" +
"\n" +
"We are our customers' trusted partner in the exciting world of safeguarding data, strengthening cybersecurity, and strategic consulting. We lead the way in ensuring data integrity, securing information, and navigating regulatory compliance with confidence. With a blend of technical expertise and clever thinking, we redefine GRC and IT strategies, empowering organisations to shine. From wielding cyber-superpowers to mastering security frameworks like ISO 27001:2013 and NIST, we're certified wizards ready to chart a course to success, strengthen defenses, and turn challenges into opportunities to excel.\n" +
"\n" +
"\n" +
"So whether our customers are battling cyber dragons or navigating the tumultuous seas of change, Gruper is their trusted ally on the journey to greatness.","As a platform engineer intern, you will learn how to build a next-generation machine learning platform, which incorporates our secret sauce, UML (unsupervised machine learning) with other SML (supervised machine learning) algorithms. Our team works to improve our core detection algorithms and automate the full training process. As complex fraud attacks become more prevalent, it is more important than ever to detect fraudsters in real-time. The platform team is responsible for developing the architecture that makes real-time UML possible. We are looking for creative and eager-to-learn engineers to help us expand our novel streaming and database systems, which enable our detection capabilities.\n" +
"\n" +
"This position is ideal for those who are majoring in Computer Science or Computer engineering who would like to gain some hands-on experience in fraud detection and machine learning before graduation. This 3 to 6 months internship could lead to a full-time position after graduation.\n" +
"\n" +
"\n" +
"What you'll do:\n" +
"\n" +
"Design and build machine learning systems that process data sets from the world’s largest consumer services\n" +
"Use unsupervised machine learning, supervised machine learning, and deep learning to detect fraudulent behavior and catch fraudsters\n" +
"Build and optimize systems, tools, and validation strategies to support new features\n" +
"Help design/build distributed real-time systems and features\n" +
"Use big data technologies (e.g. Spark, Hadoop, HBase, Cassandra) to build large scale machine learning pipelines\n" +
"Develop new systems on top of realtime streaming technologies (e.g. Kafka, Flink)\n" +
"Requirements\n" +
"\n" +
"BS/MS students majoring in Computer Science, Engineering or a related subject, Current students enrolled in a post-secondary program (BS or MS) who are majoring in computer science, information management, or a related field of Ireland based college or university. Ideally in his/her last school year\n" +
"Canadian citizen, permanent resident of Ireland\n" +
"Proven working experience in Java, Shell, Python development\n" +
"Excellent knowledge of Relational Databases, SQL and ORM technologies (JPA2, Hibernate) is a plus\n" +
"Experience in Cassandra, HBase, Flink, Spark or Kafka is a plus.\n" +
"Experience in the Spring Framework is a plus\n" +
"Experience with test-driven development is a plus\n" +
"Strong communication and interpersonal skills.\n" +
"Benefits\n" +
"\n" +
"Gain valuable hands-on experience\n" +
"Work closely with experienced professionals in the field.\n" +
"Opportunity to contribute to real projects\n" +
"Flexible schedule and hybrid work."
    );    
    
    private StanfordCoreNLP pipeline;
    

    @PostConstruct
    public void init() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma");
        pipeline = new StanfordCoreNLP(props);
    }


        public String parseResume(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename().toLowerCase();
        String text;

        if (fileName.endsWith(".pdf")) {
            text = parsePDF(file);
        } else if (fileName.endsWith(".docx")) {
            text = parseDocx(file);
        } else {
            throw new IllegalArgumentException("Unsupported file type. Only PDF and DOCX files are supported.");
        }
        
        CoreDocument document = new CoreDocument(text);
        pipeline.annotate(document);//passes document into pipeline
        
        StringBuilder result = new StringBuilder();
        
        for(CoreLabel token : document.tokens()){
            
        String word = token.word();
            // Part-of-speech tag: noun, verb, adjective, etc. (e.g., NN = noun, VB = verb)
        String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);

        // Named Entity Recognition tag: e.g., PERSON, ORGANIZATION, LOCATION, DATE, etc.
        String ner = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);

        // Lemma: the base/dictionary form of the word (e.g., "running" → "run")
        String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
        
        result.append(String.format("Word: %-15s POS: %-6s NER: %-12s Lemma: %-15s\n", word, pos, ner, lemma));
            
        }
        System.out.println(result.toString());
        return result.toString();

    }
    public int extractKeywords(String text) {
        CoreDocument document = new CoreDocument(text);
        pipeline.annotate(document);

        Set<String> stopwords = Set.of("the", "and", "is", "a", "an", "of", "to", "in", "for", "on", "with");
        List<String> keywords = new ArrayList<>();

        for (CoreLabel token : document.tokens()) {
            String lemma = token.get(CoreAnnotations.LemmaAnnotation.class).toLowerCase();
            String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);

            if ((pos.startsWith("NN") || pos.startsWith("VB") || pos.startsWith("JJ") || pos.startsWith("RB"))
                && lemma.length() > 2 
                && !stopwords.contains(lemma)) {
                keywords.add(lemma);
            }
        }

        return compareJobDesc(keywords);
    }



public int compareJobDesc(List<String> resumeKeywords) {
    if (resumeKeywords == null || resumeKeywords.isEmpty()) return 0;
    
    // Log resume keywords in debug mode
    System.out.println("Resume Keywords: " + resumeKeywords);
    
    // Preprocess resume keywords (lowercase and convert to lemmas)
    Set<String> processedResumeKeywords = resumeKeywords.stream()
        .map(String::toLowerCase)
        .filter(kw -> kw.length() > 2)
        .collect(Collectors.toSet());
    
    // Common English stopwords
    Set<String> stopwords = Set.of("the", "and", "is", "a", "an", "of", "to", "in", "for", "on", "with");
    
    double totalScore = 0;
    int validJobsProcessed = 0;
    
    for (String jobDesc : jobDescriptions) {
        if (jobDesc == null || jobDesc.trim().isEmpty()) continue;
        
        CoreDocument document = new CoreDocument(jobDesc);
        pipeline.annotate(document);
        
        // Extract relevant keywords from job description
        Set<String> jobKeywords = document.tokens().stream()
            .filter(token -> {
                String lemma = token.get(CoreAnnotations.LemmaAnnotation.class).toLowerCase();
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                return (pos.startsWith("NN") || pos.startsWith("VB") || 
                        pos.startsWith("JJ") || pos.startsWith("RB")) &&
                        lemma.length() > 2 && 
                        !stopwords.contains(lemma);
            })
            .map(token -> token.get(CoreAnnotations.LemmaAnnotation.class).toLowerCase())
            .collect(Collectors.toSet());
        
        if (jobKeywords.isEmpty()) continue;
        
        // Find matched keywords with partial matching
        Set<String> matchedKeywords = new HashSet<>();
        for (String resumeKeyword : processedResumeKeywords) {
            // Exact match
            if (jobKeywords.contains(resumeKeyword)) {
                matchedKeywords.add(resumeKeyword);
            } else {
                // Partial match - check if any job keyword contains this resume keyword
                // or if this resume keyword contains any job keyword
                for (String jobKeyword : jobKeywords) {
                    if (jobKeyword.contains(resumeKeyword) || resumeKeyword.contains(jobKeyword)) {
                        if (jobKeyword.length() > 3 && resumeKeyword.length() > 3) {
                            matchedKeywords.add(resumeKeyword);
                            break;
                        }
                    }
                }
            }
        }
        
        // Calculate scores with boosted weight
        double matchResume = processedResumeKeywords.isEmpty() ? 0 : 
                            (double) matchedKeywords.size() / processedResumeKeywords.size();
        double matchJob = (double) matchedKeywords.size() / jobKeywords.size();
        
        // Enhanced scoring formula with bias toward higher scores
        double harmonicMean = 0;
        if (matchResume > 0 || matchJob > 0) {
            // Add a small bias term to increase the score
            harmonicMean = 2 * (matchResume * matchJob + 0.05) / (matchResume + matchJob + 0.1);
        }
        
        double score = harmonicMean * 100;
        totalScore += score;
        validJobsProcessed++;
    }
    
    // Calculate final weighted score with higher multiplier
    int finalScore = validJobsProcessed == 0 ? 0 : 
                    (int) Math.round((totalScore / validJobsProcessed) * 2.0);
    
    // Cap the score at 100
    finalScore = Math.min(100, finalScore);
    
    System.out.println("Final score: " + finalScore);
    return finalScore;
}




    public String parsePDF(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream();
             PDDocument document = PDDocument.load(inputStream)) {
            
            // Create PDF stripper with specific configuration
            PDFTextStripper stripper = new PDFTextStripper() {
                @Override
                protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                    // Remove any null characters that might cause issues
                    text = text.replace("\u0000", "");
                    super.writeString(text, textPositions);
                  
                }
            };
            
            // Configure the stripper
            stripper.setSortByPosition(true);
            stripper.setStartPage(1);
            stripper.setEndPage(document.getNumberOfPages());
            stripper.setAddMoreFormatting(true);
            stripper.setSpacingTolerance(0.5f);
            
            // Extract text with enhanced error handling
            try {
                String extractedText = stripper.getText(document);
                
                // Handle potential encoding issues
                if (extractedText == null || extractedText.trim().isEmpty()) {
                    // Try alternative encoding
                    stripper.setLineSeparator("\n");
                    stripper.setWordSeparator(" ");
                    extractedText = stripper.getText(document);
                }
                
                // Post-process the extracted text
                if (extractedText != null) {
                    // Remove any null characters or invalid Unicode
                    extractedText = extractedText.replaceAll("\\p{C}", " ")
                                              .replaceAll("\\u0000", "")
                                              .replaceAll("[^\\p{Print}\\p{Space}]", " ");
                    
                    // Normalize whitespace
                    extractedText = extractedText.replaceAll("\\s+", " ")
                                              .replaceAll("(?m)^\\s+", "")
                                              .trim();
                    
                    return extractedText;
                }
                
                throw new IOException("Failed to extract text from PDF");
            } catch (Exception e) {
                // If standard extraction fails, try fallback method
                return extractTextFallback(document);
            }
        } catch (Exception e) {
            throw new IOException("Error processing PDF file: " + e.getMessage(), e);
        }
    }

    private String extractTextFallback(PDDocument document) throws IOException {
        StringBuilder text = new StringBuilder();
        
        // Try to extract text page by page
        for (int i = 0; i < document.getNumberOfPages(); i++) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(i + 1);
            stripper.setEndPage(i + 1);
            
            try {
                String pageText = stripper.getText(document);
                if (pageText != null && !pageText.trim().isEmpty()) {
                    text.append(pageText).append("\n");
                }
            } catch (Exception e) {
                // Log the error but continue with next page
                System.err.println("Error extracting text from page " + (i + 1) + ": " + e.getMessage());
            }
        }
        
        String result = text.toString().trim();
        if (result.isEmpty()) {
            throw new IOException("Could not extract any text from the PDF file");
        }
        
        return result;
    }

    private String parseDocx(MultipartFile file) throws IOException {
        try (XWPFDocument document = new XWPFDocument(file.getInputStream())) {
            StringBuilder text = new StringBuilder();
            
            // Process paragraphs
            for (XWPFParagraph para : document.getParagraphs()) {
                String paraText = para.getText().trim();
                if (!paraText.isEmpty()) {
                    text.append(paraText).append("\n");
                }
            }
            
            return text.toString();
        }
    }
    
}
