package com.mainexample.ktpm_wk3.main;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;
import com.mainexample.ktpm_wk3.DirExplorer;

import java.io.File;
import java.util.Optional;

public class CommonOperation_2 {
    public static void listMethodCalls(File projectDir) {
        new DirExplorer((level, path, file) -> path.endsWith(".java"), (level,
                                                                        path, file) -> {
            System.out.println(path);
            System.out.println(Strings.repeat("=", path.length()));
            try {
                new VoidVisitorAdapter<Object>() {
                     @Override
                     public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                        super.visit(n, arg);
                        Optional<Comment> oCmt = n.getComment();
                        if (oCmt.isEmpty()) {
                            System.out.println("Class "+n.getNameAsString()+" has no comment");
                        }
                        else {
                            Comment cmt = oCmt.get();
                            String content = cmt.getContent();
                            if(!content.contains("@Author")||(!content.contains("@Created-Date")))
                                System.out.println("Class "+n.getNameAsString()+" has comment but not have author or created date");
                        }
                     }
                }.visit(StaticJavaParser.parse(file), null);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).explore(projectDir);
    }
    public static void main(String[] args) {
        File projectDir = new File("D:\\Workspaces\\Java\\Project_No.4_TeamNo.19");
        listMethodCalls(projectDir);
    }
}
